/**
 * @file impact_analyzer.h
 * 影响分析模块头文件
 * Impact Analysis Module Header
 * <p>
 * 基于依赖链分析结果，按前端页面/后端接口/数据库表分类输出影响范围。
 * Based on dependency chain analysis results, classifies impact scope by frontend page/backend API/database table.
 * 骨架阶段返回占位数据。
 * Returns placeholder data in skeleton phase.
 * </p>
 *
 * @author DeltaTest
 */

#ifndef DELTA_ENGINE_IMPACT_ANALYZER_H
#define DELTA_ENGINE_IMPACT_ANALYZER_H

#include "bridge.h"

#ifdef __cplusplus
extern "C" {
#endif

/**
 * 执行影响范围分析 / Execute impact scope analysis
 * <p>
 * Phase 1 将集成 tree-sitter 依赖链分析实现真实的影响范围计算。
 * Phase 1 will integrate tree-sitter dependency chain analysis for real impact computation.
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
ImpactResult impact_analyzer_analyze(
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

#endif /* DELTA_ENGINE_IMPACT_ANALYZER_H */
