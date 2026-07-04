/**
 * @file bridge.h
 * C/C++ 桥接层头文件
 * C/C++ Bridge Layer Header
 * <p>
 * 提供 C 业务逻辑与 C++ gRPC 层之间的类型转换和调用接口。
 * Provides type conversion and calling interface between C business logic and C++ gRPC layer.
 * </p>
 *
 * @author DeltaTest
 */

#ifndef DELTA_ENGINE_BRIDGE_H
#define DELTA_ENGINE_BRIDGE_H

#include <stddef.h>

#ifdef __cplusplus
extern "C" {
#endif

/**
 * Diff 计算结果 / Diff computation result
 */
typedef struct {
    int code;                /**< 响应码 / Response code */
    const char* message;     /**< 响应消息 / Response message */
    int file_diff_count;     /**< 文件差异数量 / File diff count */
} DiffResult;

/**
 * 影响分析结果 / Impact analysis result
 */
typedef struct {
    int code;                /**< 响应码 / Response code */
    const char* message;     /**< 响应消息 / Response message */
    int impact_count;        /**< 影响范围数量 / Impact scope count */
} ImpactResult;

/**
 * 计算代码差异 / Compute code diff
 * <p>
 * 骨架阶段返回占位数据。
 * Returns placeholder data in skeleton phase.
 * </p>
 *
 * @param repo_id 仓库ID / Repository ID
 * @param repo_url 仓库URL / Repository URL
 * @param branch 分支名称 / Branch name
 * @param start_commit 起始提交 / Start commit
 * @param end_commit 结束提交 / End commit
 * @param credential_type 凭证类型 / Credential type
 * @param credential_key 凭证密钥 / Credential key
 * @return Diff 计算结果 / Diff computation result
 */
DiffResult bridge_compute_diff(
    long long repo_id,
    const char* repo_url,
    const char* branch,
    const char* start_commit,
    const char* end_commit,
    const char* credential_type,
    const char* credential_key
);

/**
 * 分析影响范围 / Analyze impact scope
 * <p>
 * 骨架阶段返回占位数据。
 * Returns placeholder data in skeleton phase.
 * </p>
 *
 * @param repo_id 仓库ID / Repository ID
 * @param repo_url 仓库URL / Repository URL
 * @param branch 分支名称 / Branch name
 * @param changed_files 变更文件列表 / Changed files list
 * @param changed_file_count 变更文件数量 / Changed file count
 * @param credential_type 凭证类型 / Credential type
 * @param credential_key 凭证密钥 / Credential key
 * @return 影响分析结果 / Impact analysis result
 */
ImpactResult bridge_analyze_impact(
    long long repo_id,
    const char* repo_url,
    const char* branch,
    const char** changed_files,
    int changed_file_count,
    const char* credential_type,
    const char* credential_key
);

#ifdef __cplusplus
}
#endif

#endif /* DELTA_ENGINE_BRIDGE_H */
