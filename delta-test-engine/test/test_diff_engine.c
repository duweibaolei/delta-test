/**
 * @file test_diff_engine.c
 * Diff 引擎单元测试
 * Diff Engine Unit Test
 * <p>
 * 验证 bridge_compute_diff 骨架实现的返回值。
 * Verifies bridge_compute_diff skeleton implementation return values.
 * </p>
 *
 * @author DeltaTest
 */

#include <stdio.h>
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
    DiffResult result = bridge_compute_diff(
        1,                          /* repo_id / Repository ID */
        "https://github.com/test",  /* repo_url / Repository URL */
        "main",                     /* branch / Branch name */
        "abc123",                   /* start_commit / Start commit */
        "def456",                   /* end_commit / End commit */
        "ssh",                      /* credential_type / Credential type */
        "ssh-rsa AAA..."            /* credential_key / Credential key */
    );

    ASSERT_EQ(result.code, 200);
    ASSERT_EQ(result.file_diff_count, 0);

    printf("test_diff_engine: ALL PASSED\n");
    return 0;
}
