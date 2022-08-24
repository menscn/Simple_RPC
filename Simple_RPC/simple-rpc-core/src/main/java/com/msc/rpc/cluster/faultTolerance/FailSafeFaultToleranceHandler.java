package com.msc.rpc.cluster.faultTolerance;

import com.msc.rpc.cluster.api.FaultToleranceHandler;
import com.msc.rpc.cluster.api.support.ClusterInvoker;
import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.protocol.api.InvokeParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.cluster.faultTolerance
 * @Description: 集群容错机制之安全失败 -》 RPC调用失败后忽略异常且不再重试,通常用于写日志等操作
 */
@Slf4j
public class FailSafeFaultToleranceHandler implements FaultToleranceHandler {
    /**
     * @return com.msc.rpc.common.domain.RPCResponse
     * @Author mSc
     * @Description 相比于快速失败，安全失败不会报错，但也不会重试
     * @Param [clusterInvoker, invokeParam, e]
     **/
    @Override
    public RPCResponse handle(ClusterInvoker clusterInvoker, InvokeParam invokeParam, RPCException e) {
        log.info("调用出现异常(集群容错:failsafe),requestID:{}", invokeParam.getRequestID());
        throw e;
    }
}