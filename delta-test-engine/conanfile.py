"""
DeltaTest C 引擎依赖管理
DeltaTest C Engine Dependency Management

使用 Conan 2.x 管理第三方依赖，确保与 Java 侧 gRPC 版本对齐。
Uses Conan 2.x to manage third-party dependencies, ensuring alignment with Java side gRPC version.
"""

from conan import ConanFile
from conan.tools.cmake import CMakeToolchain, CMake, cmake_layout, CMakeDeps


class DeltaTestEngineConan(ConanFile):
    """
    DeltaTest C 引擎依赖管理
    DeltaTest C Engine Dependency Management
    """
    name = "delta-test-engine"
    version = "1.0.0"
    license = "Apache-2.0"
    author = "DeltaTest"
    description = "双模式驱动的Web自动化测试平台 - C计算引擎 / DeltaTest C Calculation Engine"

    # ==================== 二进制配置 / Binary Configuration ====================
    settings = "os", "compiler", "build_type", "arch"
    options = {"shared": [True, False], "fPIC": [True, False]}
    default_options = {
        "shared": False,
        "fPIC": True,
        # gRPC/Protobuf 静态链接，与 Java 侧版本对齐
        # Static link gRPC/Protobuf, aligned with Java side version
        "grpc/*:shared": False,
        "protobuf/*:shared": False,
    }

    # ==================== 依赖声明 / Dependencies ====================
    def requirements(self):
        # gRPC 1.69.0 — 与 Java 侧 grpc.version 对齐
        # Aligned with Java side grpc.version
        self.requires("grpc/1.69.0")
        # spdlog 1.x — 高性能异步日志 / High-performance async logging
        self.requires("spdlog/1.15.1")

        # Phase 1 依赖（骨架阶段仅声明，不启用）：
        # Phase 1 dependencies (declared but not enabled in skeleton phase):
        # self.requires("libgit2/1.9.0")
        # self.requires("cjson/1.7.18")
        # self.requires("pcre2/10.44")

    def build_requirements(self):
        # CMake 构建工具（使用系统已安装的 CMake，无需 Conan 安装）
        # CMake build tool (use system-installed CMake, no need for Conan install)
        # self.tool_requires("cmake/[>=3.28]")
        pass

    # ==================== 布局 / Layout ====================
    def layout(self):
        cmake_layout(self)

    # ==================== 生成器 / Generators ====================
    def generate(self):
        deps = CMakeDeps(self)
        deps.generate()
        tc = CMakeToolchain(self)
        tc.generate()

    # ==================== 构建 / Build ====================
    def build(self):
        cmake = CMake(self)
        cmake.configure()
        cmake.build()

    # ==================== 包定义 / Package ====================
    def package(self):
        cmake = CMake(self)
        cmake.install()

    def package_info(self):
        self.cpp_info.libs = ["delta-test-engine"]

    # ==================== 配置选项 / Configure Options ====================
    def config_options(self):
        if self.settings.os == "Windows":
            del self.options.fPIC
