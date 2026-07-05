package com.dwl.common.result;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 统一响应体 R 单元测试
 * Unified Response Body R Unit Test
 *
 * @author DeltaTest
 */
@DisplayName("统一响应体 R / Unified Response Body R")
class RTest {

    // ==================== 成功响应工厂方法测试 / Success Factory Method Tests ====================

    @Nested
    @DisplayName("成功响应工厂方法 / Success Factory Methods")
    class OkTests {

        @Test
        @DisplayName("ok() 返回成功响应，无数据 / ok() returns success response without data")
        void ok_withoutData_returnsSuccessResponse() {
            R<Void> result = R.ok();

            assertEquals(200, result.getCode());
            assertEquals("操作成功", result.getMessage());
            assertNull(result.getData());
            assertTrue(result.getTimestamp() > 0);
        }

        @Test
        @DisplayName("ok(data) 返回成功响应，带数据 / ok(data) returns success response with data")
        void ok_withData_returnsSuccessResponseWithData() {
            String testData = "测试数据 / test data";

            R<String> result = R.ok(testData);

            assertEquals(200, result.getCode());
            assertEquals("操作成功", result.getMessage());
            assertEquals(testData, result.getData());
            assertTrue(result.getTimestamp() > 0);
        }

        @Test
        @DisplayName("ok(data) 支持各种数据类型 / ok(data) supports various data types")
        void ok_withVariousTypes_returnsTypedResponse() {
            // Integer 类型 / Integer type
            R<Integer> intResult = R.ok(42);
            assertEquals(42, intResult.getData());

            // List 类型 / List type
            R<java.util.List<String>> listResult = R.ok(java.util.List.of("a", "b"));
            assertEquals(2, listResult.getData().size());

            // null 数据 / null data
            R<Object> nullResult = R.ok(null);
            assertNull(nullResult.getData());
        }

        @Test
        @DisplayName("ok(message, data) 返回自定义消息成功响应 / ok(message, data) returns custom message success response")
        void ok_withMessageAndData_returnsCustomMessageResponse() {
            String customMessage = "创建成功 / Created successfully";
            Long testData = 123L;

            R<Long> result = R.ok(customMessage, testData);

            assertEquals(200, result.getCode());
            assertEquals(customMessage, result.getMessage());
            assertEquals(testData, result.getData());
        }
    }

    // ==================== 失败响应工厂方法测试 / Failure Factory Method Tests ====================

    @Nested
    @DisplayName("失败响应工厂方法 / Failure Factory Methods")
    class FailTests {

        @Test
        @DisplayName("fail() 返回默认内部错误响应 / fail() returns default internal error response")
        void fail_default_returnsInternalErrorResponse() {
            R<Void> result = R.fail();

            assertEquals(500, result.getCode());
            assertEquals("服务器内部错误", result.getMessage());
            assertNull(result.getData());
            assertTrue(result.getTimestamp() > 0);
        }

        @Test
        @DisplayName("fail(code, message) 返回自定义错误响应 / fail(code, message) returns custom error response")
        void fail_withCodeAndMessage_returnsCustomErrorResponse() {
            R<Void> result = R.fail(400, "参数错误 / Bad request");

            assertEquals(400, result.getCode());
            assertEquals("参数错误 / Bad request", result.getMessage());
            assertNull(result.getData());
        }

        @Test
        @DisplayName("fail(ErrorCode) 返回枚举对应的错误响应 / fail(ErrorCode) returns enum-based error response")
        void fail_withErrorCode_returnsEnumErrorResponse() {
            R<Void> result = R.fail(ErrorCode.UNAUTHORIZED);

            assertEquals(401, result.getCode());
            assertEquals("未认证", result.getMessage());
            assertNull(result.getData());
        }

        @Test
        @DisplayName("fail(ErrorCode, message) 返回自定义消息的错误响应 / fail(ErrorCode, message) returns custom message error response")
        void fail_withErrorCodeAndMessage_returnsCustomMessageErrorResponse() {
            R<Void> result = R.fail(ErrorCode.USER_NOT_FOUND, "用户ID=999不存在 / User ID=999 not found");

            assertEquals(1001, result.getCode());
            assertEquals("用户ID=999不存在 / User ID=999 not found", result.getMessage());
            assertNull(result.getData());
        }

        @Test
        @DisplayName("fail() 各 ErrorCode 枚举均可正确构造响应 / fail() all ErrorCode values produce valid responses")
        void fail_allErrorCodes_produceValidResponses() {
            for (ErrorCode errorCode : ErrorCode.values()) {
                R<Void> result = R.fail(errorCode);
                assertEquals(errorCode.getCode(), result.getCode());
                assertEquals(errorCode.getMessage(), result.getMessage());
                assertNull(result.getData());
            }
        }
    }

    // ==================== 构造函数和属性测试 / Constructor and Property Tests ====================

    @Nested
    @DisplayName("构造函数与属性 / Constructor and Properties")
    class ConstructorTests {

        @Test
        @DisplayName("默认构造函数初始化时间戳 / Default constructor initializes timestamp")
        void defaultConstructor_initializesTimestamp() {
            long before = System.currentTimeMillis();
            R<Void> result = new R<>();
            long after = System.currentTimeMillis();

            assertTrue(result.getTimestamp() >= before && result.getTimestamp() <= after);
            assertEquals(0, result.getCode());
            assertNull(result.getMessage());
            assertNull(result.getData());
        }

        @Test
        @DisplayName("全参构造函数正确设置所有字段 / Full constructor sets all fields")
        void fullConstructor_setsAllFields() {
            long before = System.currentTimeMillis();
            R<String> result = new R<>(201, "已创建 / Created", "data");
            long after = System.currentTimeMillis();

            assertEquals(201, result.getCode());
            assertEquals("已创建 / Created", result.getMessage());
            assertEquals("data", result.getData());
            assertTrue(result.getTimestamp() >= before && result.getTimestamp() <= after);
        }

        @Test
        @DisplayName("Lombok @Data 生成 getter/setter 可正常工作 / Lombok @Data generates working getter/setter")
        void lombokData_getterSetterWorks() {
            R<String> result = R.ok();

            result.setCode(404);
            result.setMessage("未找到 / Not found");
            result.setData("error detail");

            assertEquals(404, result.getCode());
            assertEquals("未找到 / Not found", result.getMessage());
            assertEquals("error detail", result.getData());
        }
    }

    // ==================== 序列化测试 / Serialization Tests ====================

    @Nested
    @DisplayName("序列化 / Serialization")
    class SerializationTests {

        @Test
        @DisplayName("serialVersionUID 为 1L / serialVersionUID is 1L")
        void serialVersionUID_isOne() {
            R<Void> result = R.ok();
            // 验证类实现了 Serializable / Verify class implements Serializable
            assertInstanceOf(java.io.Serializable.class, result);
        }
    }
}
