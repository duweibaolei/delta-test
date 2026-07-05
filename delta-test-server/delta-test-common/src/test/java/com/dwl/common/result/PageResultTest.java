package com.dwl.common.result;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 分页结果 PageResult 单元测试
 * Paginated Result PageResult Unit Test
 *
 * @author DeltaTest
 */
@DisplayName("分页结果 PageResult / Paginated Result PageResult")
class PageResultTest {

    // ==================== 构造函数测试 / Constructor Tests ====================

    @Nested
    @DisplayName("分页参数构造函数 / Pagination Parameter Constructor")
    class PaginationConstructorTests {

        @Test
        @DisplayName("标准分页：总数整除页大小 / Standard pagination: total divisible by page size")
        void standardPagination_totalDivisibleByPageSize() {
            List<String> data = List.of("a", "b");

            PageResult<String> result = new PageResult<>(data, 20, 1, 10);

            assertEquals(data, result.getList());
            assertEquals(20, result.getTotal());
            assertEquals(1, result.getPageNum());
            assertEquals(10, result.getPageSize());
            assertEquals(2, result.getPages());
        }

        @Test
        @DisplayName("非整除分页：总页数向上取整 / Non-divisible pagination: pages rounded up")
        void nonDivisiblePagination_pagesRoundedUp() {
            PageResult<String> result = new PageResult<>(List.of("a"), 21, 1, 10);

            assertEquals(3, result.getPages()); // (21 + 10 - 1) / 10 = 3
        }

        @Test
        @DisplayName("边界值：总数为0 / Edge case: total is 0")
        void zeroTotal_returnsZeroPages() {
            PageResult<String> result = new PageResult<>(Collections.emptyList(), 0, 1, 10);

            assertEquals(0, result.getPages());
            assertEquals(0, result.getTotal());
            assertTrue(result.getList().isEmpty());
        }

        @Test
        @DisplayName("边界值：总数恰好等于页大小 / Edge case: total equals page size")
        void totalEqualsPageSize_returnsExactlyOnePage() {
            PageResult<String> result = new PageResult<>(List.of("a"), 10, 1, 10);

            assertEquals(1, result.getPages());
        }

        @Test
        @DisplayName("边界值：总数比页大小多1 / Edge case: total exceeds page size by 1")
        void totalExceedsByOne_returnsTwoPages() {
            PageResult<String> result = new PageResult<>(List.of("a"), 11, 1, 10);

            assertEquals(2, result.getPages());
        }

        @Test
        @DisplayName("边界值：页大小为0时总页数为0 / Edge case: page size 0 yields 0 pages")
        void zeroPageSize_returnsZeroPages() {
            PageResult<String> result = new PageResult<>(Collections.emptyList(), 100, 1, 0);

            assertEquals(0, result.getPages());
        }

        @Test
        @DisplayName("大数据量：验证分页计算正确性 / Large data: verify pagination calculation correctness")
        void largeData_paginationCalculatedCorrectly() {
            PageResult<String> result = new PageResult<>(Collections.emptyList(), 999999, 1, 20);

            assertEquals(50000, result.getPages()); // (999999 + 20 - 1) / 20 = 50000
        }
    }

    // ==================== 全参构造函数测试 / Full Constructor Tests ====================

    @Nested
    @DisplayName("全参构造函数 / Full Argument Constructor")
    class FullConstructorTests {

        @Test
        @DisplayName("全参构造函数直接设置 pages 字段 / Full constructor directly sets pages field")
        void fullConstructor_setsPagesDirectly() {
            PageResult<String> result = new PageResult<>(List.of("a"), 100L, 1L, 10L, 7L);

            assertEquals(List.of("a"), result.getList());
            assertEquals(100L, result.getTotal());
            assertEquals(1L, result.getPageNum());
            assertEquals(10L, result.getPageSize());
            assertEquals(7L, result.getPages()); // 使用传入值而非计算值 / Uses passed value, not calculated
        }
    }

    // ==================== 默认构造函数测试 / Default Constructor Tests ====================

    @Nested
    @DisplayName("默认构造函数 / Default Constructor")
    class DefaultConstructorTests {

        @Test
        @DisplayName("默认构造函数所有字段为默认值 / Default constructor has default field values")
        void defaultConstructor_hasDefaultValues() {
            PageResult<String> result = new PageResult<>();

            assertNull(result.getList());
            assertEquals(0, result.getTotal());
            assertEquals(0, result.getPageNum());
            assertEquals(0, result.getPageSize());
            assertEquals(0, result.getPages());
        }
    }

    // ==================== Lombok 和序列化测试 / Lombok and Serialization Tests ====================

    @Nested
    @DisplayName("Lombok 与序列化 / Lombok and Serialization")
    class LombokSerializationTests {

        @Test
        @DisplayName("类实现 Serializable 接口 / Class implements Serializable")
        void implementsSerializable() {
            PageResult<String> result = new PageResult<>(List.of("a"), 1, 1, 10);
            assertInstanceOf(java.io.Serializable.class, result);
        }

        @Test
        @DisplayName("Lombok @Data 生成的 setter 可修改字段 / Lombok @Data setter can modify fields")
        void lombokSetter_canModifyFields() {
            PageResult<String> result = new PageResult<>();
            List<String> newData = List.of("x", "y");

            result.setList(newData);
            result.setTotal(50L);
            result.setPageNum(3L);
            result.setPageSize(25L);
            result.setPages(2L);

            assertEquals(newData, result.getList());
            assertEquals(50L, result.getTotal());
            assertEquals(3L, result.getPageNum());
            assertEquals(25L, result.getPageSize());
            assertEquals(2L, result.getPages());
        }
    }
}
