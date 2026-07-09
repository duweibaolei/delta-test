package com.dwl.ai.client;

import com.dwl.ai.model.AiResponse;
import com.dwl.common.exception.BusinessException;
import com.dwl.common.enums.ErrorCode;
import com.dwl.model.dto.ai.CaseGenerationDTO;
import com.dwl.model.dto.ai.RiskAssessmentDTO;
import com.dwl.model.dto.ai.RootCauseDTO;
import com.dwl.model.dto.ai.SummaryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * AiServiceClient 单元测试
 * AiServiceClient Unit Tests
 *
 * @author ByDWL
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AiServiceClient 单元测试 / AiServiceClient Unit Tests")
class AiServiceClientTest {

    @Mock
    private RestTemplate aiRestTemplate;

    private AiServiceClient aiServiceClient;

    @BeforeEach
    void setUp() {
        aiServiceClient = new AiServiceClient(aiRestTemplate);
    }

    // ==================== 健康检查 / Health Check ====================

    @Nested
    @DisplayName("健康检查 / Health Check")
    class HealthCheckTests {

        @Test
        @DisplayName("健康检查成功 — 返回UP状态 / Health check success - returns UP status")
        void healthCheck_success_returnsUpStatus() {
            // Arrange
            AiResponse response = AiResponse.builder()
                    .code(200)
                    .message("success")
                    .data(Map.of("status", "UP"))
                    .timestamp(System.currentTimeMillis())
                    .build();
            when(aiRestTemplate.getForObject("/api/health", AiResponse.class))
                    .thenReturn(response);

            // Act
            Map<String, Object> result = aiServiceClient.healthCheck();

            // Assert
            assertThat(result).containsEntry("status", "UP");
            verify(aiRestTemplate).getForObject("/api/health", AiResponse.class);
        }

        @Test
        @DisplayName("健康检查失败 — AI服务返回非200 / Health check fails - AI service returns non-200")
        void healthCheck_non200_throwsBusinessException() {
            // Arrange
            AiResponse response = AiResponse.builder()
                    .code(500)
                    .message("internal error")
                    .data(null)
                    .timestamp(System.currentTimeMillis())
                    .build();
            when(aiRestTemplate.getForObject("/api/health", AiResponse.class))
                    .thenReturn(response);

            // Act & Assert
            assertThatThrownBy(() -> aiServiceClient.healthCheck())
                    .isInstanceOf(BusinessException.class)
                    .satisfies(ex -> {
                        BusinessException be = (BusinessException) ex;
                        assertThat(be.getErrorCode()).isEqualTo(ErrorCode.AI_SERVICE_UNAVAILABLE);
                    });
        }

        @Test
        @DisplayName("健康检查失败 — AI服务不可达 / Health check fails - AI service unreachable")
        void healthCheck_unreachable_throwsBusinessException() {
            // Arrange
            when(aiRestTemplate.getForObject("/api/health", AiResponse.class))
                    .thenThrow(new RestClientException("Connection refused"));

            // Act & Assert
            assertThatThrownBy(() -> aiServiceClient.healthCheck())
                    .isInstanceOf(BusinessException.class)
                    .satisfies(ex -> {
                        BusinessException be = (BusinessException) ex;
                        assertThat(be.getErrorCode()).isEqualTo(ErrorCode.AI_SERVICE_UNAVAILABLE);
                    });
        }

        @Test
        @DisplayName("健康检查失败 — AI服务返回null / Health check fails - AI service returns null")
        void healthCheck_nullResponse_throwsBusinessException() {
            // Arrange
            when(aiRestTemplate.getForObject("/api/health", AiResponse.class))
                    .thenReturn(null);

            // Act & Assert
            assertThatThrownBy(() -> aiServiceClient.healthCheck())
                    .isInstanceOf(BusinessException.class)
                    .satisfies(ex -> {
                        BusinessException be = (BusinessException) ex;
                        assertThat(be.getErrorCode()).isEqualTo(ErrorCode.AI_SERVICE_UNAVAILABLE);
                    });
        }
    }

    // ==================== 风险评估 / Risk Assessment ====================

    @Nested
    @DisplayName("风险评估 / Risk Assessment")
    class RiskAssessmentTests {

        @Test
        @DisplayName("风险评估成功 / Risk assessment success")
        void assessRisk_success_returnsResponse() {
            // Arrange
            RiskAssessmentDTO dto = RiskAssessmentDTO.builder()
                    .changedFiles(List.of("src/main/java/UserService.java"))
                    .changeDescription("修改了用户登录逻辑")
                    .commitId("abc1234")
                    .build();

            AiResponse expected = AiResponse.builder()
                    .code(200)
                    .message("success")
                    .data(Map.of("risk_level", "medium"))
                    .timestamp(System.currentTimeMillis())
                    .build();

            when(aiRestTemplate.postForObject(eq("/api/risk-assessment"), any(HttpEntity.class), eq(AiResponse.class)))
                    .thenReturn(expected);

            // Act
            AiResponse result = aiServiceClient.assessRisk(dto);

            // Assert
            assertThat(result.isSuccess()).isTrue();
            assertThat(result.getData()).containsEntry("risk_level", "medium");
        }

        @Test
        @DisplayName("风险评估失败 — AI服务不可用 / Risk assessment fails - AI service unavailable")
        void assessRisk_serviceUnavailable_throwsBusinessException() {
            // Arrange
            RiskAssessmentDTO dto = RiskAssessmentDTO.builder()
                    .changedFiles(List.of("test.java"))
                    .build();

            when(aiRestTemplate.postForObject(eq("/api/risk-assessment"), any(HttpEntity.class), eq(AiResponse.class)))
                    .thenThrow(new RestClientException("Service unavailable"));

            // Act & Assert
            assertThatThrownBy(() -> aiServiceClient.assessRisk(dto))
                    .isInstanceOf(BusinessException.class)
                    .satisfies(ex -> {
                        BusinessException be = (BusinessException) ex;
                        assertThat(be.getErrorCode()).isEqualTo(ErrorCode.AI_SERVICE_UNAVAILABLE);
                    });
        }
    }

    // ==================== 变更摘要 / Change Summary ====================

    @Nested
    @DisplayName("变更摘要 / Change Summary")
    class SummaryTests {

        @Test
        @DisplayName("变更摘要成功 / Summary generation success")
        void generateSummary_success_returnsResponse() {
            // Arrange
            SummaryDTO dto = SummaryDTO.builder()
                    .changedFiles(List.of("UserService.java"))
                    .diffContent("@@ -1 +1 @@")
                    .commitId("def5678")
                    .build();

            AiResponse expected = AiResponse.builder()
                    .code(200)
                    .message("success")
                    .data(Map.of("summary", "修改了用户服务"))
                    .timestamp(System.currentTimeMillis())
                    .build();

            when(aiRestTemplate.postForObject(eq("/api/summary"), any(HttpEntity.class), eq(AiResponse.class)))
                    .thenReturn(expected);

            // Act
            AiResponse result = aiServiceClient.generateSummary(dto);

            // Assert
            assertThat(result.isSuccess()).isTrue();
            assertThat(result.getData()).containsEntry("summary", "修改了用户服务");
        }
    }

    // ==================== 根因分析 / Root Cause Analysis ====================

    @Nested
    @DisplayName("根因分析 / Root Cause Analysis")
    class RootCauseTests {

        @Test
        @DisplayName("根因分析成功 / Root cause analysis success")
        void analyzeRootCause_success_returnsResponse() {
            // Arrange
            RootCauseDTO dto = RootCauseDTO.builder()
                    .caseName("用户登录验证")
                    .errorMessage("AssertionError: expected 200 but was 401")
                    .executionLog("Step 1: navigate... Step 2: login...")
                    .taskId("TASK-001")
                    .build();

            AiResponse expected = AiResponse.builder()
                    .code(200)
                    .message("success")
                    .data(Map.of("root_cause", "Token过期导致401"))
                    .timestamp(System.currentTimeMillis())
                    .build();

            when(aiRestTemplate.postForObject(eq("/api/root-cause"), any(HttpEntity.class), eq(AiResponse.class)))
                    .thenReturn(expected);

            // Act
            AiResponse result = aiServiceClient.analyzeRootCause(dto);

            // Assert
            assertThat(result.isSuccess()).isTrue();
            assertThat(result.getData()).containsEntry("root_cause", "Token过期导致401");
        }
    }

    // ==================== 用例生成 / Case Generation ====================

    @Nested
    @DisplayName("用例生成 / Case Generation")
    class CaseGenerationTests {

        @Test
        @DisplayName("用例生成成功 / Case generation success")
        void generateCase_success_returnsResponse() {
            // Arrange
            CaseGenerationDTO dto = CaseGenerationDTO.builder()
                    .pageUrl("https://example.com/login")
                    .pageDescription("用户登录页面")
                    .requirement("验证登录功能")
                    .build();

            AiResponse expected = AiResponse.builder()
                    .code(200)
                    .message("success")
                    .data(Map.of("steps", 5))
                    .timestamp(System.currentTimeMillis())
                    .build();

            when(aiRestTemplate.postForObject(eq("/api/case-generation"), any(HttpEntity.class), eq(AiResponse.class)))
                    .thenReturn(expected);

            // Act
            AiResponse result = aiServiceClient.generateCase(dto);

            // Assert
            assertThat(result.isSuccess()).isTrue();
            assertThat(result.getData()).containsEntry("steps", 5);
        }
    }

    // ==================== 通用异常处理 / Generic Exception Handling ====================

    @Nested
    @DisplayName("通用异常处理 / Generic Exception Handling")
    class ExceptionHandlingTests {

        @Test
        @DisplayName("POST请求返回非200 — 抛出AI_SERVICE_UNAVAILABLE / POST returns non-200 - throws AI_SERVICE_UNAVAILABLE")
        void postForObject_non200_throwsBusinessException() {
            // Arrange
            RiskAssessmentDTO dto = RiskAssessmentDTO.builder()
                    .changedFiles(List.of("test.java"))
                    .build();

            AiResponse errorResponse = AiResponse.builder()
                    .code(500)
                    .message("Internal Server Error")
                    .data(null)
                    .timestamp(System.currentTimeMillis())
                    .build();

            when(aiRestTemplate.postForObject(eq("/api/risk-assessment"), any(HttpEntity.class), eq(AiResponse.class)))
                    .thenReturn(errorResponse);

            // Act & Assert
            assertThatThrownBy(() -> aiServiceClient.assessRisk(dto))
                    .isInstanceOf(BusinessException.class)
                    .satisfies(ex -> {
                        BusinessException be = (BusinessException) ex;
                        assertThat(be.getErrorCode()).isEqualTo(ErrorCode.AI_SERVICE_UNAVAILABLE);
                    });
        }

        @Test
        @DisplayName("POST请求返回null — 抛出AI_SERVICE_UNAVAILABLE / POST returns null - throws AI_SERVICE_UNAVAILABLE")
        void postForObject_nullResponse_throwsBusinessException() {
            // Arrange
            SummaryDTO dto = SummaryDTO.builder()
                    .changedFiles(List.of("test.java"))
                    .build();

            when(aiRestTemplate.postForObject(eq("/api/summary"), any(HttpEntity.class), eq(AiResponse.class)))
                    .thenReturn(null);

            // Act & Assert
            assertThatThrownBy(() -> aiServiceClient.generateSummary(dto))
                    .isInstanceOf(BusinessException.class)
                    .satisfies(ex -> {
                        BusinessException be = (BusinessException) ex;
                        assertThat(be.getErrorCode()).isEqualTo(ErrorCode.AI_SERVICE_UNAVAILABLE);
                    });
        }

        @Test
        @DisplayName("HTTP客户端异常 — 抛出AI_SERVICE_UNAVAILABLE / HTTP client error - throws AI_SERVICE_UNAVAILABLE")
        void postForObject_httpClientError_throwsBusinessException() {
            // Arrange
            RootCauseDTO dto = RootCauseDTO.builder()
                    .caseName("test")
                    .errorMessage("error")
                    .build();

            when(aiRestTemplate.postForObject(eq("/api/root-cause"), any(HttpEntity.class), eq(AiResponse.class)))
                    .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

            // Act & Assert
            assertThatThrownBy(() -> aiServiceClient.analyzeRootCause(dto))
                    .isInstanceOf(BusinessException.class)
                    .satisfies(ex -> {
                        BusinessException be = (BusinessException) ex;
                        assertThat(be.getErrorCode()).isEqualTo(ErrorCode.AI_SERVICE_UNAVAILABLE);
                    });
        }

        @Test
        @DisplayName("BusinessException直接传播 — 不被包装 / BusinessException propagates directly - not wrapped")
        void postForObject_businessException_propagatesDirectly() {
            // Arrange
            RiskAssessmentDTO dto = RiskAssessmentDTO.builder()
                    .changedFiles(List.of("test.java"))
                    .build();

            AiResponse errorResponse = AiResponse.builder()
                    .code(500)
                    .message("error")
                    .build();

            when(aiRestTemplate.postForObject(eq("/api/risk-assessment"), any(HttpEntity.class), eq(AiResponse.class)))
                    .thenReturn(errorResponse);

            // Act & Assert — 验证 BusinessException 不会被二次包装
            assertThatThrownBy(() -> aiServiceClient.assessRisk(dto))
                    .isExactlyInstanceOf(BusinessException.class);
        }
    }
}
