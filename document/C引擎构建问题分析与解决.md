# DeltaTest C 引擎构建问题分析与解决

> DeltaTest C Engine Build Troubleshooting Guide
>
> 日期：2026-07-04

---

## 目录

1. [问题一：CMake find_package(Protobuf) 失败](#问题一cmake-find_packageprotobuf-失败)
2. [问题二：MinGW 链接器路径空格报错](#问题二mingw-链接器路径空格报错)
3. [解决方案：CMakePresets 配置](#解决方案cmakepresets-配置)
4. [CLion 配置步骤](#clion-配置步骤)
5. [构建验证结果](#构建验证结果)
6. [后续规划](#后续规划)

---

## 问题一：CMake find_package(Protobuf) 失败

### 现象

在 CLion 中首次加载 `delta-test-engine` 项目时，CMake 配置失败：

```
CMake Error at CMakeLists.txt:29 (find_package):
  Could not find a package configuration file provided by "Protobuf" with any
  of the following names:

    ProtobufConfig.cmake
    protobuf-config.cmake

  Add the installation prefix of "Protobuf" to CMAKE_PREFIX_PATH or set
  "Protobuf_DIR" to a directory containing one of the above files.
```

### 根因

原始 `CMakeLists.txt` 使用 `find_package(Protobuf CONFIG REQUIRED)` 强制要求 Protobuf 包存在。但 Windows 开发环境下通常不会全局安装 Protobuf/gRPC，这些依赖需要通过 **Conan 2.x** 包管理器安装后才能被 CMake 发现。

Conan 安装依赖的流程为：

```bash
conan install . --build=missing -s build_type=Release
cmake --preset conan-release
```

在未执行 `conan install` 的情况下，`REQUIRED` 模式直接报错终止配置，导致项目无法加载。

### 修复方案

将 gRPC/Protobuf 构建改为**可选模式**，使 C 业务逻辑库始终可独立编译：

```cmake
# CMakeLists.txt 关键改动

option(ENABLE_GRPC "Build gRPC Server (requires conan install)" ON)

if(ENABLE_GRPC)
    find_package(Protobuf CONFIG QUIET)    # REQUIRED → QUIET
    find_package(gRPC CONFIG QUIET)        # REQUIRED → QUIET

    if(Protobuf_FOUND AND gRPC_FOUND)
        set(GRPC_AVAILABLE TRUE)
    else()
        message(WARNING "gRPC/Protobuf not found - building C business logic only")
        set(GRPC_AVAILABLE FALSE)
    endif()
else()
    set(GRPC_AVAILABLE FALSE)
endif()

# C 业务逻辑库始终构建
add_library(delta_engine_core STATIC ...)

# gRPC Server 仅在依赖可用时构建
if(GRPC_AVAILABLE)
    add_executable(delta-test-engine ...)
endif()
```

### 构建模式矩阵

| 模式 | 依赖条件 | 构建产物 |
|------|----------|----------|
| `ENABLE_GRPC=OFF` | 无需任何依赖 | `libdelta_engine_core.a` + 2 个 CTest |
| `ENABLE_GRPC=ON`（默认） | 无 Protobuf/gRPC | `libdelta_engine_core.a` + 2 个 CTest（降级，显示 WARNING） |
| `ENABLE_GRPC=ON` + Conan 已安装 | Protobuf/gRPC 可用 | `libdelta_engine_core.a` + `delta-test-engine` 可执行文件 + 3 个 CTest |

---

## 问题二：MinGW 链接器路径空格报错

### 现象

CMake 配置成功后，编译阶段 C 静态库通过，但链接测试可执行文件时失败：

```
[ 55%] Built target delta_engine_core
[  77%] Linking C executable test_diff_engine.exe
collect2.exe: error: ld returned 1 exit status
```

详细错误输出：

```
/usr/bin/ld: 找不到 G:/software/Development/CLion/CLion\: No such file or directory
/usr/bin/ld: 找不到 2026.1.3/bin/mingw/bin/../lib/gcc/x86_64-w64-mingw32/13.1.0/../../../../x86_64-w64-mingw32/lib/../lib/crt2.o: No such file or directory
/usr/bin/ld: 找不到 -lmingw32: No such file or directory
/usr/bin/ld: 找不到 -lgcc: No such file or directory
/usr/bin/ld: 找不到 -lgcc_eh: No such file or directory
/usr/bin/ld: 找不到 -lmoldname: No such file or directory
/usr/bin/ld: 找不到 -lmingwex: No such file or directory
/usr/bin/ld: 找不到 -lmsvcrt: No such file or directory
```

### 根因

CLion 自带的 MinGW 安装路径为：

```
G:\software\Development\CLion\CLion 2026.1.3\bin\mingw\
```

路径中包含**空格**（`CLion 2026.1.3`）。MinGW 的 `ld.exe` 在解析库搜索路径时，无法正确处理含空格的路径，将其在空格处截断：

- 期望路径：`G:/software/Development/CLion/CLion 2026.1.3/bin/mingw/lib/gcc/...`
- 实际解析：`G:/software/Development/CLion/CLion` + `2026.1.3/bin/mingw/lib/gcc/...`

这是 MinGW `ld.exe` 的已知 Bug，在 Windows 平台上路径含空格时会触发。

### 修复方案

使用系统上独立安装的 **MinGW64**（路径无空格）替代 CLion 自带 MinGW：

```
G:/software/Development/mingw64/          ← 无空格，ld 可正确解析
G:/software/Development/CLion/CLion 2026.1.3/bin/mingw/  ← 含空格，ld 解析失败
```

---

## 解决方案：CMakePresets 配置

为避免每次手动指定编译器参数，创建了 `CMakePresets.json` 文件，让 CLion 和命令行都能自动使用正确的工具链。

### 文件位置

```
delta-test-engine/CMakePresets.json
```

### Preset 定义

| Preset 名称 | 用途 | gRPC | 工具链 |
|-------------|------|------|--------|
| `mingw-debug` | 日常开发调试 | OFF | MinGW64 (GCC 8.1.0) |
| `mingw-release` | 性能测试 | OFF | MinGW64 (GCC 8.1.0) |
| `conan-release` | 完整 gRPC 构建 | ON | MinGW64 + Conan Toolchain |

### 使用方式

```bash
# 方式一：使用 CMake Preset（推荐）
cmake --preset mingw-debug
cmake --build --preset mingw-debug
ctest --preset mingw-debug

# 方式二：手动指定参数
cmake -DCMAKE_C_COMPILER=G:/software/Development/mingw64/bin/gcc.exe ^
      -DCMAKE_CXX_COMPILER=G:/software/Development/mingw64/bin/g++.exe ^
      -DENABLE_GRPC=OFF ^
      -G "MinGW Makefiles" ^
      -B cmake-build-debug
cmake --build cmake-build-debug
```

---

## CLion 配置步骤

### 方式一：使用 CMake Presets（推荐）

1. 打开 CLion，加载 `delta-test-engine` 项目
2. CLion 会自动检测 `CMakePresets.json`
3. **File → Reload CMake Project**
4. 在 CMake 配置面板的 **Profile** 下拉框中选择 `mingw-debug`
5. 点击 **Build** 即可

### 方式二：手动配置 CMake 选项

1. **File → Settings → Build, Execution, Deployment → CMake**
2. 点击 **+** 添加新 Profile
3. 填写配置：
   - **Name**: `debug-no-grpc`
   - **Build type**: `Debug`
   - **Generator**: `MinGW Makefiles`
   - **CMake options**:
     ```
     -DCMAKE_C_COMPILER=G:/software/Development/mingw64/bin/gcc.exe
     -DCMAKE_CXX_COMPILER=G:/software/Development/mingw64/bin/g++.exe
     -DCMAKE_MAKE_PROGRAM=G:/software/Development/mingw64/bin/mingw32-make.exe
     -DENABLE_GRPC=OFF
     ```
4. 点击 **Apply** → **OK**
5. **File → Reload CMake Project**

### 方式三：配置自定义工具链

1. **File → Settings → Build, Execution, Deployment → Toolchains**
2. 点击 **+** 添加 MinGW 工具链：
   - **Name**: `MinGW64`
   - **Environment**: `G:/software/Development/mingw64`
3. 在 CMake 配置中将 **Toolchain** 选择为 `MinGW64`

---

## 构建验证结果

### mingw-debug Preset 验证

```
-- The C compiler identification is GNU 8.1.0
-- The CXX compiler identification is GNU 8.1.0
-- gRPC Server build disabled / gRPC 服务器构建已禁用
-- Configuring done (18.2s)
-- Generating done (0.9s)

[ 55%] Built target delta_engine_core
[ 77%] Built target test_diff_engine
[100%] Built target test_impact_analyzer

Test project F:/work-space/idea/FullyDevelop/DeltaTest/delta-test-engine/cmake-build-debug
1/2 Test #1: test_diff_engine .................   Passed
2/2 Test #2: test_impact_analyzer .............   Passed

100% tests passed, 0 tests failed out of 2
```

### 构建产物汇总

| 目标 | 类型 | 状态 |
|------|------|------|
| `delta_engine_core` | C 静态库 | ✅ 编译通过 |
| `test_diff_engine` | CTest 可执行文件 | ✅ 测试通过 |
| `test_impact_analyzer` | CTest 可执行文件 | ✅ 测试通过 |
| `delta-test-engine` | gRPC Server 可执行文件 | ⏸️ 跳过（需 Conan） |
| `test_grpc_server` | gRPC 编译验证测试 | ⏸️ 跳过（需 Conan） |

---

## 后续规划

### gRPC 完整构建（Docker/Linux 环境）

Windows 下 Conan 安装 gRPC 存在兼容性问题（缓存损坏、MSVC/GCC 冲突等），建议通过 Docker 构建：

```bash
# 使用项目 Dockerfile（基于 conanio/gcc12 + debian:bookworm-slim）
docker build -t delta-test-engine .

# 或手动步骤
docker run -it conanio/gcc12 bash
conan install . --build=missing -s build_type=Release
cmake --preset conan-release
cmake --build --preset conan-release
```

### 已知版本对齐问题

| 组件 | Java 侧版本 | C 引擎 Conan 版本 | 状态 |
|------|-------------|-------------------|------|
| Protobuf | 4.29.3 (protoc) | 5.29.6 (Conan 解析) | ⚠️ 需验证序列化兼容性 |
| gRPC | 1.69.0 | 1.69.0 | ✅ 对齐 |

如发现 Protobuf 序列化不兼容，需在 `conanfile.py` 中显式覆盖：

```python
self.requires("protobuf/4.29.3", override=True)
```

### Phase 1 依赖

骨架阶段完成后，Phase 1 将启用以下依赖：

| 依赖 | 用途 | 状态 |
|------|------|------|
| libgit2/1.9.0 | Git 仓库操作（clone/pull/diff） | 已声明，未启用 |
| cjson/1.7.18 | JSON 解析 | 已声明，未启用 |
| pcre2/10.44 | 正则表达式匹配 | 已声明，未启用 |
| tree-sitter | AST 解析（需手动集成） | 未声明 |
