/**
 * @file bridge.c
 * C/C++ 桥接层实现
 * C/C++ Bridge Layer Implementation
 * <p>
 * 将 gRPC 层的请求参数转换为 C 类型，调用业务逻辑模块，
 * 将结果转换为桥接层结构体返回。
 * Converts gRPC layer request parameters to C types, calls business logic modules,
 * converts results to bridge layer structs for return.
 * </p>
 *
 * @author ByDWL
 */

#include "bridge.h"
#include "diff_engine.h"
#include "impact_analyzer.h"

/**
 * 计算代码差异 / Compute code diff
 * <p>
 * 委托给 diff_engine_compute 执行实际计算。
 * Delegates to diff_engine_compute for actual computation.
 * </p>
 */
DiffResult bridge_compute_diff(
    const long long repo_id,
    const char* repo_url,
    const char* branch,
    const char* start_commit,
    const char* end_commit,
    const char* credential_type,
    const char* credential_key
) {
    return diff_engine_compute(repo_id, repo_url, branch, start_commit, end_commit, credential_type, credential_key);
}

/**
 * 分析影响范围 / Analyze impact scope
 * <p>
 * 委托给 impact_analyzer_analyze 执行实际分析。
 * Delegates to impact_analyzer_analyze for actual analysis.
 * </p>
 */
ImpactResult bridge_analyze_impact(
    const long long repo_id,
    const char* repo_url,
    const char* branch,
    const char** changed_files,
    const int changed_file_count,
    const char* credential_type,
    const char* credential_key
) {
    return impact_analyzer_analyze(repo_id, repo_url, branch, changed_files, changed_file_count, credential_type, credential_key);
}
