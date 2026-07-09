/**
 * @file dependency_graph.h
 * 依赖图模块头文件
 * Dependency Graph Module Header
 * <p>
 * 基于 tree-sitter 解析 AST，构建 import/call 依赖图，计算影响范围。
 * Based on tree-sitter AST parsing, builds import/call dependency graph, computes impact scope.
 * 骨架阶段返回占位数据。
 * Returns placeholder data in skeleton phase.
 * </p>
 *
 * @author ByDWL
 */

#ifndef DELTA_ENGINE_DEPENDENCY_GRAPH_H
#define DELTA_ENGINE_DEPENDENCY_GRAPH_H

#ifdef __cplusplus
extern "C" {
#endif

/**
 * 构建依赖图（占位实现）
 * Build dependency graph (placeholder implementation)
 * <p>
 * Phase 1 将集成 tree-sitter 实现真实的依赖图构建。
 * Phase 1 will integrate tree-sitter for real dependency graph construction.
 * </p>
 *
 * @param repo_path 仓库路径 / Repository path
 * @return 0 成功，非0 失败 / 0 success, non-zero failure
 */
int dependency_graph_build(const char* repo_path);

#ifdef __cplusplus
}
#endif

#endif /* DELTA_ENGINE_DEPENDENCY_GRAPH_H */
