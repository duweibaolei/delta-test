/**
 * @file diff_engine.h
 * Diff 计算模块头文件
 * Diff Computation Module Header
 * <p>
 * 接收 commit 范围，调用 libgit2 计算 diff，输出变更文件列表与变更行号。
 * Receives commit range, calls libgit2 to compute diff, outputs changed file list and line numbers.
 * 骨架阶段返回占位数据。
 * Returns placeholder data in skeleton phase.
 * </p>
 *
 * @author DeltaTest
 */

#ifndef DELTA_ENGINE_DIFF_ENGINE_H
#define DELTA_ENGINE_DIFF_ENGINE_H

#include "bridge.h"

#ifdef __cplusplus
extern "C" {
#endif

/**
 * 执行代码差异计算 / Execute code diff computation
 * <p>
 * Phase 1 将集成 libgit2 实现真实的 diff 计算。
 * Phase 1 will integrate libgit2 for real diff computation.
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
DiffResult diff_engine_compute(
    long long repo_id,
    const char* repo_url,
    const char* branch,
    const char* start_commit,
    const char* end_commit,
    const char* credential_type,
    const char* credential_key
);

#ifdef __cplusplus
}
#endif

#endif /* DELTA_ENGINE_DIFF_ENGINE_H */
