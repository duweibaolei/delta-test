package com.dwl.grpc.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * gRPC客户端配置类
 * gRPC Client Configuration
 * <p>
 * 配置与C计算引擎的gRPC连接通道和Stub。
 * Configures gRPC channel and stub for connecting to the C calculation engine.
 * </p>
 *
 * @author DeltaTest
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

    /**
     * gRPC管理通道
     * gRPC managed channel
     *
     * @return ManagedChannel
     */
    @Bean
    public ManagedChannel grpcManagedChannel() {
        log.info("初始化gRPC通道: {}:{} / Initializing gRPC channel: {}:{}", engineHost, enginePort);
        return ManagedChannelBuilder.forAddress(engineHost, enginePort)
                .usePlaintext()
                .build();
    }
}
