package com.dwl.grpc.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * gRPC客户端配置类
 * gRPC Client Configuration
 * <p>
 * 配置与C计算引擎的gRPC连接通道。
 * 支持明文模式(开发环境)和TLS模式(生产环境)。
 * TLS模式默认使用JDK系统信任库验证服务端证书，也可指定自定义CA证书。
 * Configures gRPC channel for connecting to the C calculation engine.
 * Supports plaintext mode (dev) and TLS mode (prod).
 * TLS mode uses JDK system trust store by default, or a custom CA cert can be specified.
 * </p>
 *
 * @author ByDWL
 */
@Slf4j
@Configuration
public class GrpcClientConfig {

    /** gRPC引擎主机 / gRPC engine host */
    @Value("${grpc.client.engine.host:localhost}")
    private String engineHost;

    /** gRPC引擎端口 / gRPC engine port */
    @Value("${grpc.client.engine.port:9090}")
    private int enginePort;

    /** 是否使用TLS / Whether to use TLS */
    @Value("${grpc.client.engine.use-tls:false}")
    private boolean useTls;

    /** 自定义CA证书路径（可选，为空则使用系统默认信任库）/ Custom CA cert path (optional, empty = system default trust store) */
    @Value("${grpc.client.engine.trust-cert-path:}")
    private String trustCertPath;

    /**
     * gRPC管理通道
     * gRPC managed channel
     * <p>
     * 根据配置创建明文或TLS通道。开发环境使用明文，生产环境使用TLS。
     * TLS模式默认使用JDK系统信任库（cacerts）验证服务端证书，
     * 也可通过 trust-cert-path 指定自定义CA证书文件。
     * Creates plaintext or TLS channel based on configuration.
     * Dev uses plaintext, prod uses TLS.
     * TLS mode uses JDK system trust store (cacerts) by default,
     * or a custom CA cert can be specified via trust-cert-path.
     * </p>
     *
     * @return ManagedChannel
     */
    @Bean
    public ManagedChannel grpcManagedChannel() {
        log.info("初始化gRPC通道: {}:{}, TLS: {}, trustCertPath: {} / Initializing gRPC channel: {}:{}, TLS: {}, trustCertPath: {}",
                engineHost, enginePort, useTls,
                trustCertPath.isEmpty() ? "(system default)" : trustCertPath,
                engineHost, enginePort, useTls,
                trustCertPath.isEmpty() ? "(system default)" : trustCertPath);

        if (useTls) {
            // TLS模式：使用NettyChannelBuilder + SslContext / TLS mode: use NettyChannelBuilder + SslContext
            try {
                SslContext sslContext = buildTlsSslContext();
                return NettyChannelBuilder.forAddress(engineHost, enginePort)
                        .sslContext(sslContext)
                        .build();
            } catch (Exception e) {
                log.error("gRPC TLS配置失败 / gRPC TLS configuration failed", e);
                throw new RuntimeException("gRPC TLS配置失败 / gRPC TLS configuration failed", e);
            }
        } else {
            // 明文模式：仅用于开发环境 / Plaintext mode: dev only
            return ManagedChannelBuilder.forAddress(engineHost, enginePort)
                    .usePlaintext()
                    .build();
        }
    }

    /**
     * 构建TLS SslContext
     * Build TLS SslContext
     * <p>
     * 如果指定了 trust-cert-path，则使用自定义CA证书；
     * 否则使用JDK系统默认信任库（cacerts）验证服务端证书。
     * If trust-cert-path is specified, uses the custom CA certificate;
     * otherwise uses the JDK system default trust store (cacerts) to verify server certificates.
     * </p>
     *
     * @return SslContext
     * @throws Exception SSL上下文构建失败 / SSL context build failed
     */
    private SslContext buildTlsSslContext() throws Exception {
        if (trustCertPath != null && !trustCertPath.isEmpty()) {
            // 使用自定义CA证书 / Use custom CA certificate
            File certFile = new File(trustCertPath);
            if (!certFile.exists()) {
                throw new IllegalArgumentException(
                        "gRPC TLS自定义CA证书文件不存在 / gRPC TLS custom CA cert file not found: " + trustCertPath);
            }
            log.info("使用自定义CA证书: {} / Using custom CA certificate: {}", trustCertPath, trustCertPath);
            return GrpcSslContexts.forClient()
                    .trustManager(certFile)
                    .build();
        } else {
            // 使用JDK系统默认信任库 / Use JDK system default trust store
            log.info("使用JDK系统默认信任库 / Using JDK system default trust store");
            return GrpcSslContexts.forClient()
                    .build();
        }
    }
}
