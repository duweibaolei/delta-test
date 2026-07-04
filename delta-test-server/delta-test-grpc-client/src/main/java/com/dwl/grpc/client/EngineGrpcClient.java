package com.dwl.grpc.client;

import com.dwl.grpc.engine.CodeAnalysisServiceGrpc;
import com.dwl.grpc.engine.DiffRequest;
import com.dwl.grpc.engine.DiffResponse;
import com.dwl.grpc.engine.HealthCheckRequest;
import com.dwl.grpc.engine.HealthCheckResponse;
import com.dwl.grpc.engine.ImpactRequest;
import com.dwl.grpc.engine.ImpactResponse;
import com.dwl.common.exception.BusinessException;
import com.dwl.common.result.ErrorCode;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * C计算引擎 gRPC 客户端服务
 * C Calculation Engine gRPC Client Service
 * <p>
 * 封装与 C 计算引擎的 gRPC 通信，提供差异计算、影响分析和健康检查能力。
 * 当前为骨架阶段，仅建立通信链路，Phase 1 将补充超时/重试/降级策略。
 * Wraps gRPC communication with the C calculation engine, providing diff computation,
 * impact analysis, and health check capabilities.
 * Currently in skeleton phase, only establishing communication channel.
 * Phase 1 will add timeout/retry/fallback strategies.
 * </p>
 *
 * @author DeltaTest
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EngineGrpcClient {

    /** gRPC 管理通道 / gRPC managed channel */
    private final ManagedChannel grpcManagedChannel;

    /**
     * 计算代码差异
     * Compute code diff
     * <p>
     * 调用 C 引擎的 ComputeDiff RPC 方法，计算两个 commit 之间的代码差异。
     * Calls the C engine's ComputeDiff RPC method to compute code diff between two commits.
     * </p>
     *
     * @param repoId          仓库ID / Repository ID
     * @param repoUrl         仓库URL / Repository URL
     * @param branch          分支名称 / Branch name
     * @param startCommit     起始提交 / Start commit
     * @param endCommit       结束提交 / End commit
     * @param credentialType  凭证类型 / Credential type
     * @param credentialKey   凭证密钥 / Credential key
     * @return 代码差异响应 / Diff response
     * @throws BusinessException gRPC 调用失败时抛出 / Thrown when gRPC call fails
     */
    public DiffResponse computeDiff(
            long repoId,
            String repoUrl,
            String branch,
            String startCommit,
            String endCommit,
            String credentialType,
            String credentialKey) {

        DiffRequest request = DiffRequest.newBuilder()
                .setRepoId(repoId)
                .setRepoUrl(repoUrl)
                .setBranch(branch)
                .setStartCommit(startCommit)
                .setEndCommit(endCommit)
                .setCredentialType(credentialType)
                .setCredentialKey(credentialKey)
                .build();

        try {
            CodeAnalysisServiceGrpc.CodeAnalysisServiceBlockingStub stub =
                    CodeAnalysisServiceGrpc.newBlockingStub(grpcManagedChannel);
            DiffResponse response = stub.computeDiff(request);
            log.info("ComputeDiff 调用成功: repoId={}, code={}, message={} / " +
                    "ComputeDiff call succeeded: repoId={}, code={}, message={}",
                    repoId, response.getCode(), response.getMessage(),
                    repoId, response.getCode(), response.getMessage());
            return response;
        } catch (StatusRuntimeException e) {
            log.error("ComputeDiff 调用失败: repoId={}, status={} / " +
                    "ComputeDiff call failed: repoId={}, status={}",
                    repoId, e.getStatus(), repoId, e.getStatus(), e);
            throw new BusinessException(ErrorCode.ENGINE_UNAVAILABLE,
                    "C引擎 ComputeDiff 调用失败 / C engine ComputeDiff call failed: " + e.getStatus());
        }
    }

    /**
     * 分析影响范围
     * Analyze impact scope
     * <p>
     * 调用 C 引擎的 AnalyzeImpact RPC 方法，基于变更文件列表分析影响范围。
     * Calls the C engine's AnalyzeImpact RPC method to analyze impact scope based on changed files.
     * </p>
     *
     * @param repoId          仓库ID / Repository ID
     * @param repoUrl         仓库URL / Repository URL
     * @param branch          分支名称 / Branch name
     * @param changedFiles    变更文件列表 / Changed files list
     * @param credentialType  凭证类型 / Credential type
     * @param credentialKey   凭证密钥 / Credential key
     * @return 影响分析响应 / Impact analysis response
     * @throws BusinessException gRPC 调用失败时抛出 / Thrown when gRPC call fails
     */
    public ImpactResponse analyzeImpact(
            long repoId,
            String repoUrl,
            String branch,
            List<String> changedFiles,
            String credentialType,
            String credentialKey) {

        ImpactRequest.Builder requestBuilder = ImpactRequest.newBuilder()
                .setRepoId(repoId)
                .setRepoUrl(repoUrl)
                .setBranch(branch)
                .addAllChangedFiles(changedFiles)
                .setCredentialType(credentialType)
                .setCredentialKey(credentialKey);

        try {
            CodeAnalysisServiceGrpc.CodeAnalysisServiceBlockingStub stub =
                    CodeAnalysisServiceGrpc.newBlockingStub(grpcManagedChannel);
            ImpactResponse response = stub.analyzeImpact(requestBuilder.build());
            log.info("AnalyzeImpact 调用成功: repoId={}, code={}, message={} / " +
                    "AnalyzeImpact call succeeded: repoId={}, code={}, message={}",
                    repoId, response.getCode(), response.getMessage(),
                    repoId, response.getCode(), response.getMessage());
            return response;
        } catch (StatusRuntimeException e) {
            log.error("AnalyzeImpact 调用失败: repoId={}, status={} / " +
                    "AnalyzeImpact call failed: repoId={}, status={}",
                    repoId, e.getStatus(), repoId, e.getStatus(), e);
            throw new BusinessException(ErrorCode.ENGINE_UNAVAILABLE,
                    "C引擎 AnalyzeImpact 调用失败 / C engine AnalyzeImpact call failed: " + e.getStatus());
        }
    }

    /**
     * 健康检查
     * Health check
     * <p>
     * 调用 C 引擎的 HealthCheck RPC 方法，检查引擎服务是否可用。
     * Calls the C engine's HealthCheck RPC method to check if the engine service is available.
     * </p>
     *
     * @return 健康检查响应 / Health check response
     * @throws BusinessException gRPC 调用失败时抛出 / Thrown when gRPC call fails
     */
    public HealthCheckResponse healthCheck() {
        HealthCheckRequest request = HealthCheckRequest.newBuilder().build();

        try {
            CodeAnalysisServiceGrpc.CodeAnalysisServiceBlockingStub stub =
                    CodeAnalysisServiceGrpc.newBlockingStub(grpcManagedChannel);
            HealthCheckResponse response = stub.healthCheck(request);
            log.info("HealthCheck 调用成功: healthy={}, version={} / " +
                    "HealthCheck call succeeded: healthy={}, version={}",
                    response.getHealthy(), response.getVersion(),
                    response.getHealthy(), response.getVersion());
            return response;
        } catch (StatusRuntimeException e) {
            log.error("HealthCheck 调用失败: status={} / " +
                    "HealthCheck call failed: status={}",
                    e.getStatus(), e.getStatus(), e);
            throw new BusinessException(ErrorCode.ENGINE_UNAVAILABLE,
                    "C引擎 HealthCheck 调用失败 / C engine HealthCheck call failed: " + e.getStatus());
        }
    }
}
