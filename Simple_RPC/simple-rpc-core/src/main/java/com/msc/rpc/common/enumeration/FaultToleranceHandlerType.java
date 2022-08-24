package com.msc.rpc.common.enumeration;

import com.msc.rpc.cluster.api.FaultToleranceHandler;
import com.msc.rpc.cluster.faultTolerance.FailFastFaultToleranceHandler;
import com.msc.rpc.cluster.faultTolerance.FailOverFaultToleranceHandler;
import com.msc.rpc.cluster.faultTolerance.FailSafeFaultToleranceHandler;
import com.msc.rpc.common.enumeration.support.ExtensionBaseType;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.enumeration
 * @Description: 集群容错机制
 */
public enum FaultToleranceHandlerType implements ExtensionBaseType<FaultToleranceHandler> {
    FAILOVER(new FailOverFaultToleranceHandler()),     //失败切换
    FAILFAST(new FailFastFaultToleranceHandler()),     //快速失败
    FAILSAFE(new FailSafeFaultToleranceHandler());     //安全失败

    private FaultToleranceHandler faultToleranceHandler;

    /**
     * @return
     * @Author mSc
     * @Description 传入上面三种参数
     * @Param [faultToleranceHandler]
     **/
    FaultToleranceHandlerType(FaultToleranceHandler faultToleranceHandler) {
        this.faultToleranceHandler = faultToleranceHandler;
    }

    @Override
    public FaultToleranceHandler getInstance() {
        return faultToleranceHandler;
    }
}