package com.msc.rpc.cluster.api;

import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.config.ReferenceConfig;
import com.msc.rpc.protocol.api.Invoker;

import java.util.List;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.cluster.api
 * @Description: 负载均衡
 */
public interface LoadBalancer {
    /**
     * @return com.msc.rpc.protocol.api.Invoker<T>
     * @Author mSc
     * @Description 提交集群
     * @Param [referenceConfig]
     **/
    <T> Invoker<T> referCluster(ReferenceConfig<T> referenceConfig);

    /**
     * @return com.msc.rpc.protocol.api.Invoker
     * @Author mSc
     * @Description 选择调用
     * @Param [availableInvokers, request]
     **/
    Invoker select(List<Invoker> availableInvokers, RPCRequest request);
}
