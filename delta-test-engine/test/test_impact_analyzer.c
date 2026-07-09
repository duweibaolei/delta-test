/**
 * @file test_impact_analyzer.c
 * 影响分析单元测试
 * Impact Analyzer Unit Test
 * <p>
 * 验证 bridge_analyze_impact 骨架实现的返回值。
 * Verifies bridge_analyze_impact skeleton implementation return values.
 * </p>
 *
 * @author ByDWL
 */

#include <stdio.h>
#include <stdlib.h>
#include "bridge.h"

/**
 * 简单断言宏 / Simple assertion macro
 */
#define ASSERT_EQ(a, b) do { \
    if ((a) != (b)) { \
        fprintf(stderr, "ASSERTION FAILED: %s == %s (line %d)\n", #a, #b, __LINE__); \
        return 1; \
    } \
} while(0)

/**
 * 测试入口 / Test entry point
 *
 * @return 0 全部通过 / 0 all passed
 */
int main(void) {
    /* ==================== 测试1: 骨架占位响应 / Test 1: Skeleton placeholder response ==================== */
    const char* changed_files[] = {
        "src/main/java/com/dwl/service/UserService.java",
        "src/main/resources/application.yml"
    };

    ImpactResult result = bridge_analyze_impact(
        1,                          /* repo_id / Repository ID */
        "https://github.com/test",  /* repo_url / Repository URL */
        "main",                     /* branch / Branch name */
        changed_files,              /* changed_files / Changed files list */
        2,                          /* changed_file_count / Changed file count */
        "ssh",                      /* credential_type / Credential type */
        "ssh-rsa AAA..."            /* credential_key / Credential key */
    );

    ASSERT_EQ(result.code, 200);
    ASSERT_EQ(result.impact_count, 0);

    printf("test_impact_analyzer: ALL PASSED\n");
    return 0;
}
