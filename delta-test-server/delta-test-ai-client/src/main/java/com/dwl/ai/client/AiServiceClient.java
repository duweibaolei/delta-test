package com.dwl.ai.client;

import com.dwl.ai.model.AiResponse;
import com.dwl.common.exception.BusinessException;
import com.dwl.common.result.ErrorCode;
import com.dwl.model.dto.ai.CaseGenerationDTO;
import com.dwl.model.dto.ai.RiskAssessmentDTO;
import com.dwl.model.dto.ai.RootCauseDTO;
import com.dwl.model.dto.ai.SummaryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Python AI服务 HTTP 客户端
 * Python AI Service HTTP Client
 * <p>
 * 封装与 Python AI 服务的 HTTP 通信，提供健康检查、风险评估、变更摘要、根因分析、用例生成能力。
 * 当前为骨架阶段，仅建立通信链路，Phase 1 将补充重试/降级策略。
 * Wraps HTTP communication with the Python AI service, providing health check,
 * risk assessment, change summary, root cause analysis, and test case generation capabilities.
 * Currently in skeleton phase, only establishing communication channel.
 * Phase 1 will add retry/fallback strategies.
 * </p>
 * <p>
 * 注意：使用显式构造器注入而非 @RequiredArgsConstructor，
 * 因为 Lombok 不会自动将 @Qualifier 复制到构造函数参数。
 * Note: Uses explicit constructor injection instead of @RequiredArgsConstructor,
 * because Lombok doesn't automatically copy @Qualifier to constructor parameters.
 * </p>
 *
 * @author DeltaTest
 */
@Slf4j
@Service
public class AiServiceClient {

    /** AI服务RestTemplate / AI service RestTemplate */
    private final RestTemplate aiRestTemplate;

    /**
     * 构造函数 — 通过 @Qualifier 指定注入 aiRestTemplate Bean
     * Constructor - injects aiRestTemplate Bean via @Qualifier
     *
     * @param aiRestTemplate AI服务专用的RestTemplate / AI service specific RestTemplate
     */
    public AiServiceClient(@Qualifier("aiRestTemplate") RestTemplate aiRestTemplate) {
        this.aiRestTemplate = aiRestTemplate;
    }

    /**
     * 健康检查
     * Health check
     * <p>
     * 调用 Python AI 服务的 /api/health 端点，检查服务是否可用。
     * Calls the Python AI service's /api/health endpoint to check if the service is available.
     * </p>
     *
     * @return AI服务健康状态 / AI service health status
     * @throws BusinessException AI服务不可用时抛出 / Thrown when AI service is unavailable
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> healthCheck() {
        try {
            AiResponse response = aiRestTemplate.getForObject("/api/health", AiResponse.class);
            if (response != null && response.isSuccess()) {
                log.info("AI服务 HealthCheck 调用成功: code={}, data={} / " +
                        "AI service HealthCheck call succeeded: code={}, data={}",
                        response.getCode(), response.getData(),
                        response.getCode(), response.getData());
                return response.getData();
            } else {
                log.warn("AI服务 HealthCheck 返回非200: code={}, message={} / " +
                        "AI service HealthCheck returned non-200: code={}, message={}",
                        response != null ? response.getCode() : "null",
                        response != null ? response.getMessage() : "null",
                        response != null ? response.getCode() : "null",
                        response != null ? response.getMessage() : "null");
                throw new BusinessException(ErrorCode.AI_SERVICE_UNAVAILABLE,
                        "AI服务健康检查返回异常 / AI service health check returned abnormal: " +
                                (response != null ? response.getCode() : "null"));
            }
        } catch (BusinessException e) {
            throw e;
        } catch (RestClientException e) {
            log.error("AI服务 HealthCheck 调用失败 / AI service HealthCheck call failed", e);
            throw new BusinessException(ErrorCode.AI_SERVICE_UNAVAILABLE,
                    "AI服务 HealthCheck 调用失败 / AI service HealthCheck call failed: " + e.getMessage());
        }
    }

    /**
     * 风险评估
     * Risk assessment
     * <p>
     * 调用 Python AI 服务的 /api/risk-assessment 端点，基于变更文件列表评估风险等级。
     * Calls the Python AI service's /api/risk-assessment endpoint to assess risk level based on changed files.
     * </p>
     *
     * @param dto 风险评估请求 / Risk assessment request
     * @return AI服务响应 / AI service response
     * @throws BusinessException AI服务不可用时抛出 / Thrown when AI service is unavailable
     */
    public AiResponse assessRisk(RiskAssessmentDTO dto) {
        return postForObject("/api/risk-assessment", dto);
    }

    /**
     * 变更摘要
     * Change summary
     * <p>
     * 调用 Python AI 服务的 /api/summary 端点，基于变更详情生成变更说明与测试建议。
     * Calls the Python AI service's /api/summary endpoint to generate change description and test recommendations.
     * </p>
     *
     * @param dto 变更摘要请求 / Change summary request
     * @return AI服务响应 / AI service response
     * @throws BusinessException AI服务不可用时抛出 / Thrown when AI service is unavailable
     */
    public AiResponse generateSummary(SummaryDTO dto) {
        return postForObject("/api/summary", dto);
    }

    /**
     * 根因分析
     * Root cause analysis
     * <p>
     * 调用 Python AI 服务的 /api/root-cause 端点，分析失败用例的可能原因与修复建议。
     * Calls the Python AI service's /api/root-cause endpoint to analyze possible causes and fix suggestions.
     * </p>
     *
     * @param dto 根因分析请求 / Root cause analysis request
     * @return AI服务响应 / AI service response
     * @throws BusinessException AI服务不可用时抛出 / Thrown when AI service is unavailable
     */
    public AiResponse analyzeRootCause(RootCauseDTO dto) {
        return postForObject("/api/root-cause", dto);
    }

    /**
     * 用例生成
     * Test case generation
     * <p>
     * 调用 Python AI 服务的 /api/case-generation 端点，根据页面描述自动生成测试步骤与断言。
     * Calls the Python AI service's /api/case-generation endpoint to auto-generate test steps and assertions.
     * </p>
     *
     * @param dto 用例生成请求 / Case generation request
     * @return AI服务响应 / AI service response
     * @throws BusinessException AI服务不可用时抛出 / Thrown when AI service is unavailable
     */
    public AiResponse generateCase(CaseGenerationDTO dto) {
        return postForObject("/api/case-generation", dto);
    }

    /**
     * 通用POST请求方法
     * Generic POST request method
     * <p>
     * 封装向 AI 服务发送 POST 请求的通用逻辑，包括请求头设置、异常处理和日志记录。
     * Wraps common logic for sending POST requests to the AI service, including header setup,
     * exception handling, and logging.
     * </p>
     *
     * @param path   API路径 / API path
     * @param body   请求体 / Request body
     * @return AI服务响应 / AI service response
     * @throws BusinessException AI服务不可用时抛出 / Thrown when AI service is unavailable
     */
    private AiResponse postForObject(String path, Object body) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);

            AiResponse response = aiRestTemplate.postForObject(path, entity, AiResponse.class);
            if (response != null && response.isSuccess()) {
                log.info("AI服务 {} 调用成功: code={} / AI service {} call succeeded: code={}",
                        path, response.getCode(), path, response.getCode());
                return response;
            } else {
                log.warn("AI服务 {} 返回非200: code={}, message={} / " +
                        "AI service {} returned non-200: code={}, message={}",
                        path, response != null ? response.getCode() : "null",
                        response != null ? response.getMessage() : "null",
                        path, response != null ? response.getCode() : "null",
                        response != null ? response.getMessage() : "null");
                throw new BusinessException(ErrorCode.AI_SERVICE_UNAVAILABLE,
                        "AI服务 " + path + " 返回异常 / AI service " + path + " returned abnormal: " +
                                (response != null ? response.getCode() : "null"));
            }
        } catch (BusinessException e) {
            throw e;
        } catch (RestClientException e) {
            log.error("AI服务 {} 调用失败 / AI service {} call failed", path, path, e);
            throw new BusinessException(ErrorCode.AI_SERVICE_UNAVAILABLE,
                    "AI服务 " + path + " 调用失败 / AI service " + path + " call failed: " + e.getMessage());
        }
    }
}
