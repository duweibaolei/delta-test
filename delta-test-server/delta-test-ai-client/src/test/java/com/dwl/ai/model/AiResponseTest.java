package com.dwl.ai.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * AiResponse 单元测试
 * AiResponse Unit Tests
 *
 * @author DeltaTest
 */
@DisplayName("AiResponse 单元测试 / AiResponse Unit Tests")
class AiResponseTest {

    @Nested
    @DisplayName("isSuccess 判断 / isSuccess Check")
    class IsSuccessTests {

        @Test
        @DisplayName("code=200 — isSuccess返回true / code=200 - isSuccess returns true")
        void isSuccess_code200_returnsTrue() {
            AiResponse response = AiResponse.builder().code(200).message("success").build();
            assertThat(response.isSuccess()).isTrue();
        }

        @Test
        @DisplayName("code=500 — isSuccess返回false / code=500 - isSuccess returns false")
        void isSuccess_code500_returnsFalse() {
            AiResponse response = AiResponse.builder().code(500).message("error").build();
            assertThat(response.isSuccess()).isFalse();
        }

        @Test
        @DisplayName("code=0 — isSuccess返回false / code=0 - isSuccess returns false")
        void isSuccess_code0_returnsFalse() {
            AiResponse response = AiResponse.builder().code(0).message("unknown").build();
            assertThat(response.isSuccess()).isFalse();
        }
    }

    @Nested
    @DisplayName("构建器与字段 / Builder and Fields")
    class BuilderTests {

        @Test
        @DisplayName("全字段构建 — 所有字段正确赋值 / Full field build - all fields correctly assigned")
        void builder_allFields_assigned() {
            long now = System.currentTimeMillis();
            AiResponse response = AiResponse.builder()
                    .code(200)
                    .message("success")
                    .data(Map.of("status", "UP"))
                    .timestamp(now)
                    .build();

            assertThat(response.getCode()).isEqualTo(200);
            assertThat(response.getMessage()).isEqualTo("success");
            assertThat(response.getData()).containsEntry("status", "UP");
            assertThat(response.getTimestamp()).isEqualTo(now);
        }

        @Test
        @DisplayName("无参构造 — 默认值为零值 / No-arg constructor - default zero values")
        void noArgConstructor_defaultValues() {
            AiResponse response = new AiResponse();
            assertThat(response.getCode()).isEqualTo(0);
            assertThat(response.getMessage()).isNull();
            assertThat(response.getData()).isNull();
            assertThat(response.getTimestamp()).isEqualTo(0L);
        }

        @Test
        @DisplayName("Lombok Setter — 字段可修改 / Lombok Setter - fields modifiable")
        void lombokSetter_modifiesFields() {
            AiResponse response = new AiResponse();
            response.setCode(200);
            response.setMessage("ok");
            response.setData(Map.of("key", "value"));
            response.setTimestamp(1234567890L);

            assertThat(response.getCode()).isEqualTo(200);
            assertThat(response.getMessage()).isEqualTo("ok");
            assertThat(response.getData()).containsEntry("key", "value");
            assertThat(response.getTimestamp()).isEqualTo(1234567890L);
        }
    }
}
