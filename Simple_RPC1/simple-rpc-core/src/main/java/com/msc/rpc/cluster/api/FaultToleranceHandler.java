package com.msc.rpc.cluster.api;

import com.msc.rpc.cluster.api.support.ClusterInvoker;
import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.protocol.api.InvokeParam;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.cluster.api
 * @Description: 集群容错处理器:调用失败后的处理机制
 * 常见的处理机制有：failover(失败自动切换)、failfast(快速失败)、failsafe(安全失败)等
 * 注意:容错机制仅对同步调用有效，因为异步调用的response都是直接返回null
 */
public interface FaultToleranceHandler {
    /**
     * @return com.msc.rpc.common.domain.RPCResponse
     * @Author mSc
     * @Description 集群容错处理方法, 配置不同的容错机制，会有不同的实现
     * @Param [clusterInvoker, invokeParam, e]
     **/
    RPCResponse handle(ClusterInvoker clusterInvoker, InvokeParam invokeParam, RPCException e);
}
