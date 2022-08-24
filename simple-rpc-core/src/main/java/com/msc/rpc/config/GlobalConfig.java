package com.msc.rpc.config;

import com.msc.rpc.cluster.api.FaultToleranceHandler;
import com.msc.rpc.cluster.api.LoadBalancer;
import com.msc.rpc.executor.api.TaskExecutor;
import com.msc.rpc.protocol.api.Protocol;
import com.msc.rpc.proxy.api.RPCProxyFactory;
import com.msc.rpc.registry.api.ServiceRegistry;
import com.msc.rpc.serialize.api.Serializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.config
 * @Description: 全局配置里类 -》维护几个核心配置类（单例）的实例
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalConfig {
    private ApplicationConfig applicationConfig;
    private ClusterConfig clusterConfig;
    private RegistryConfig registryConfig;
    private ProtocolConfig protocolConfig;

    public Serializer getSerializer() {
        return applicationConfig.getSerializerInstance();
    }

    public RPCProxyFactory getProxyFactory() {
        return applicationConfig.getProxyFactoryInstance();
    }

    public LoadBalancer getLoadBalancer() {
        return clusterConfig.getLoadBalanceInstance();
    }

    public FaultToleranceHandler getFaultToleranceHandler() {
        return clusterConfig.getFaultToleranceHandlerInstance();
    }

    public ServiceRegistry getServiceRegistry() {
        return registryConfig.getRegistryInstance();
    }

    public Protocol getProtocol() {
        return protocolConfig.getProtocolInstance();
    }

    public TaskExecutor getClientExecutor() {
        return protocolConfig.getExecutors().getClient().getTaskExecutorInstance();
    }

    public TaskExecutor getServerExecutor() {
        return protocolConfig.getExecutors().getServer().getTaskExecutorInstance();
    }

    public int getPort() {
        return protocolConfig.getPort();
    }
}
