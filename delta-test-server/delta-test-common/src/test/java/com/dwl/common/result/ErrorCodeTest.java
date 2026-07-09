package com.dwl.common.result;

import com.dwl.common.enums.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 错误码枚举 ErrorCode 单元测试
 * Error Code Enumeration ErrorCode Unit Test
 *
 * @author ByDWL
 */
@DisplayName("错误码枚举 ErrorCode / Error Code Enumeration ErrorCode")
class ErrorCodeTest {

    // ==================== 通用错误码测试 / Generic HTTP Error Code Tests ====================

    @Nested
    @DisplayName("通用HTTP错误码 / Generic HTTP Error Codes")
    class GenericErrorCodesTests {

        @Test
        @DisplayName("SUCCESS: code=200, message=操作成功 / SUCCESS: code=200, message=ok")
        void success() {
            assertEquals(200, ErrorCode.SUCCESS.getCode());
            assertEquals("操作成功", ErrorCode.SUCCESS.getMessage());
        }

        @Test
        @DisplayName("BAD_REQUEST: code=400 / BAD_REQUEST: code=400")
        void badRequest() {
            assertEquals(400, ErrorCode.BAD_REQUEST.getCode());
            assertEquals("请求参数错误", ErrorCode.BAD_REQUEST.getMessage());
        }

        @Test
        @DisplayName("UNAUTHORIZED: code=401 / UNAUTHORIZED: code=401")
        void unauthorized() {
            assertEquals(401, ErrorCode.UNAUTHORIZED.getCode());
            assertEquals("未认证", ErrorCode.UNAUTHORIZED.getMessage());
        }

        @Test
        @DisplayName("FORBIDDEN: code=403 / FORBIDDEN: code=403")
        void forbidden() {
            assertEquals(403, ErrorCode.FORBIDDEN.getCode());
            assertEquals("无权限", ErrorCode.FORBIDDEN.getMessage());
        }

        @Test
        @DisplayName("NOT_FOUND: code=404 / NOT_FOUND: code=404")
        void notFound() {
            assertEquals(404, ErrorCode.NOT_FOUND.getCode());
            assertEquals("资源不存在", ErrorCode.NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("CONFLICT: code=409 / CONFLICT: code=409")
        void conflict() {
            assertEquals(409, ErrorCode.CONFLICT.getCode());
            assertEquals("数据冲突", ErrorCode.CONFLICT.getMessage());
        }

        @Test
        @DisplayName("INTERNAL_ERROR: code=500 / INTERNAL_ERROR: code=500")
        void internalError() {
            assertEquals(500, ErrorCode.INTERNAL_ERROR.getCode());
            assertEquals("服务器内部错误", ErrorCode.INTERNAL_ERROR.getMessage());
        }

        @Test
        @DisplayName("UPSTREAM_UNAVAILABLE: code=502 / UPSTREAM_UNAVAILABLE: code=502")
        void upstreamUnavailable() {
            assertEquals(502, ErrorCode.UPSTREAM_UNAVAILABLE.getCode());
            assertEquals("上游服务不可用", ErrorCode.UPSTREAM_UNAVAILABLE.getMessage());
        }

        @Test
        @DisplayName("SERVICE_UNAVAILABLE: code=503 / SERVICE_UNAVAILABLE: code=503")
        void serviceUnavailable() {
            assertEquals(503, ErrorCode.SERVICE_UNAVAILABLE.getCode());
            assertEquals("服务暂不可用", ErrorCode.SERVICE_UNAVAILABLE.getMessage());
        }
    }

    // ==================== 用户模块错误码测试 / User Module Error Code Tests ====================

    @Nested
    @DisplayName("用户模块错误码 (1xxx) / User Module Error Codes (1xxx)")
    class UserModuleErrorCodesTests {

        @Test
        @DisplayName("USER_NOT_FOUND: code=1001 / USER_NOT_FOUND: code=1001")
        void userNotFound() {
            assertEquals(1001, ErrorCode.USER_NOT_FOUND.getCode());
            assertEquals("用户不存在", ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("USER_PASSWORD_ERROR: code=1002 / USER_PASSWORD_ERROR: code=1002")
        void userPasswordError() {
            assertEquals(1002, ErrorCode.USER_PASSWORD_ERROR.getCode());
            assertEquals("密码错误", ErrorCode.USER_PASSWORD_ERROR.getMessage());
        }

        @Test
        @DisplayName("USER_DISABLED: code=1003 / USER_DISABLED: code=1003")
        void userDisabled() {
            assertEquals(1003, ErrorCode.USER_DISABLED.getCode());
            assertEquals("用户已禁用", ErrorCode.USER_DISABLED.getMessage());
        }

        @Test
        @DisplayName("TOKEN_EXPIRED: code=1004 / TOKEN_EXPIRED: code=1004")
        void tokenExpired() {
            assertEquals(1004, ErrorCode.TOKEN_EXPIRED.getCode());
            assertEquals("Token已过期", ErrorCode.TOKEN_EXPIRED.getMessage());
        }

        @Test
        @DisplayName("TOKEN_INVALID: code=1005 / TOKEN_INVALID: code=1005")
        void tokenInvalid() {
            assertEquals(1005, ErrorCode.TOKEN_INVALID.getCode());
            assertEquals("Token无效", ErrorCode.TOKEN_INVALID.getMessage());
        }
    }

    // ==================== 测试用例模块错误码测试 / Test Case Module Error Code Tests ====================

    @Nested
    @DisplayName("测试用例模块错误码 (2xxx) / Test Case Module Error Codes (2xxx)")
    class TestCaseModuleErrorCodesTests {

        @Test
        @DisplayName("CASE_NOT_FOUND: code=2001 / CASE_NOT_FOUND: code=2001")
        void caseNotFound() {
            assertEquals(2001, ErrorCode.CASE_NOT_FOUND.getCode());
            assertEquals("测试用例不存在", ErrorCode.CASE_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("CASE_VERSION_CONFLICT: code=2002 / CASE_VERSION_CONFLICT: code=2002")
        void caseVersionConflict() {
            assertEquals(2002, ErrorCode.CASE_VERSION_CONFLICT.getCode());
            assertEquals("用例版本冲突", ErrorCode.CASE_VERSION_CONFLICT.getMessage());
        }
    }

    // ==================== 测试任务模块错误码测试 / Test Task Module Error Code Tests ====================

    @Nested
    @DisplayName("测试任务模块错误码 (3xxx) / Test Task Module Error Codes (3xxx)")
    class TestTaskModuleErrorCodesTests {

        @Test
        @DisplayName("TASK_NOT_FOUND: code=3001 / TASK_NOT_FOUND: code=3001")
        void taskNotFound() {
            assertEquals(3001, ErrorCode.TASK_NOT_FOUND.getCode());
            assertEquals("测试任务不存在", ErrorCode.TASK_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("TASK_ALREADY_RUNNING: code=3002 / TASK_ALREADY_RUNNING: code=3002")
        void taskAlreadyRunning() {
            assertEquals(3002, ErrorCode.TASK_ALREADY_RUNNING.getCode());
            assertEquals("任务已在执行中", ErrorCode.TASK_ALREADY_RUNNING.getMessage());
        }
    }

    // ==================== 测试报告模块错误码测试 / Test Report Module Error Code Tests ====================

    @Nested
    @DisplayName("测试报告模块错误码 (4xxx) / Test Report Module Error Codes (4xxx)")
    class TestReportModuleErrorCodesTests {

        @Test
        @DisplayName("REPORT_NOT_FOUND: code=4001 / REPORT_NOT_FOUND: code=4001")
        void reportNotFound() {
            assertEquals(4001, ErrorCode.REPORT_NOT_FOUND.getCode());
            assertEquals("测试报告不存在", ErrorCode.REPORT_NOT_FOUND.getMessage());
        }
    }

    // ==================== 引擎与AI服务错误码测试 / Engine & AI Service Error Code Tests ====================

    @Nested
    @DisplayName("引擎与AI服务错误码 (5xxx) / Engine & AI Service Error Codes (5xxx)")
    class EngineAIErrorCodesTests {

        @Test
        @DisplayName("ENGINE_UNAVAILABLE: code=5001 / ENGINE_UNAVAILABLE: code=5001")
        void engineUnavailable() {
            assertEquals(5001, ErrorCode.ENGINE_UNAVAILABLE.getCode());
            assertEquals("C计算引擎不可用", ErrorCode.ENGINE_UNAVAILABLE.getMessage());
        }

        @Test
        @DisplayName("AI_SERVICE_UNAVAILABLE: code=5002 / AI_SERVICE_UNAVAILABLE: code=5002")
        void aiServiceUnavailable() {
            assertEquals(5002, ErrorCode.AI_SERVICE_UNAVAILABLE.getCode());
            assertEquals("AI服务不可用", ErrorCode.AI_SERVICE_UNAVAILABLE.getMessage());
        }
    }

    // ==================== 枚举完整性测试 / Enum Completeness Tests ====================

    @Nested
    @DisplayName("枚举完整性 / Enum Completeness")
    class EnumCompletenessTests {

        @Test
        @DisplayName("所有枚举值的 code 唯一 / All enum codes are unique")
        void allCodesAreUnique() {
            long distinctCount = java.util.Arrays.stream(ErrorCode.values())
                    .map(ErrorCode::getCode)
                    .distinct()
                    .count();
            assertEquals(ErrorCode.values().length, distinctCount,
                    "所有错误码值应唯一 / All error code values should be unique");
        }

        @Test
        @DisplayName("所有枚举值的 message 非空 / All enum messages are non-null and non-empty")
        void allMessagesAreNonEmpty() {
            for (ErrorCode errorCode : ErrorCode.values()) {
                assertNotNull(errorCode.getMessage(),
                        errorCode.name() + " 的 message 不应为 null / message should not be null");
                assertFalse(errorCode.getMessage().isEmpty(),
                        errorCode.name() + " 的 message 不应为空 / message should not be empty");
            }
        }

        @Test
        @DisplayName("枚举值总数与预期一致 / Enum count matches expected")
        void enumCount_matchesExpected() {
            // 通用HTTP(9) + 用户(5) + 用例(2) + 任务(2) + 报告(1) + 引擎AI(2) = 21
            assertEquals(21, ErrorCode.values().length);
        }

        @Test
        @DisplayName("业务错误码分段正确 / Business error codes are segmented correctly")
        void businessCodesAreSegmented() {
            // 用户模块: 1001-1999 / User module: 1001-1999
            assertTrue(ErrorCode.USER_NOT_FOUND.getCode() >= 1001 && ErrorCode.USER_NOT_FOUND.getCode() <= 1999);
            assertTrue(ErrorCode.TOKEN_INVALID.getCode() >= 1001 && ErrorCode.TOKEN_INVALID.getCode() <= 1999);

            // 用例模块: 2001-2999 / Case module: 2001-2999
            assertTrue(ErrorCode.CASE_NOT_FOUND.getCode() >= 2001 && ErrorCode.CASE_NOT_FOUND.getCode() <= 2999);

            // 任务模块: 3001-3999 / Task module: 3001-3999
            assertTrue(ErrorCode.TASK_NOT_FOUND.getCode() >= 3001 && ErrorCode.TASK_NOT_FOUND.getCode() <= 3999);

            // 报告模块: 4001-4999 / Report module: 4001-4999
            assertTrue(ErrorCode.REPORT_NOT_FOUND.getCode() >= 4001 && ErrorCode.REPORT_NOT_FOUND.getCode() <= 4999);

            // 引擎AI模块: 5001-5999 / Engine/AI module: 5001-5999
            assertTrue(ErrorCode.ENGINE_UNAVAILABLE.getCode() >= 5001 && ErrorCode.ENGINE_UNAVAILABLE.getCode() <= 5999);
        }
    }
}
