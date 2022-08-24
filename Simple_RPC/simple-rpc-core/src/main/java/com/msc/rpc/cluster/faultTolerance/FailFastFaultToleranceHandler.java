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
 * @Description: 集群容错机制:快速失败
 * RPC调用失败后立即报错且不再重试,通常用于非幂等性的写操作,如新增记录
 */
@Slf4j
public class FailFastFaultToleranceHandler implements FaultToleranceHandler {
    /**
     * @return com.msc.rpc.common.domain.RPCResponse
     * @Author mSc
     * @Description 快速失败-》 调用失败直接报错，不会再重试
     * @Param [clusterInvoker, invokeParam, e]
     **/
    @Override
    public RPCResponse handle(ClusterInvoker clusterInvoker, InvokeParam invokeParam, RPCException e) {
        log.info("调用出现异常(集群容错:failfast),requestID:{}", invokeParam.getRequestID());
        throw e;
    }
}
