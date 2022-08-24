package com.msc.rpc.cluster.loadBalance;

import com.msc.rpc.cluster.api.support.AbstractLoadBalancer;
import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.protocol.api.Invoker;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.cluster.loadBalance
 * @Description: 随机负载均衡算法
 */
public class RandomLoadBalancer extends AbstractLoadBalancer {
    /**
     * @return com.msc.rpc.protocol.api.Invoker
     * @Author mSc
     * @Description 随机的把负载分配到各个可用的服务器上
     * @Param [availableInvokers, request]
     **/
    @Override
    protected Invoker doSelect(List<Invoker> availableInvokers, RPCRequest request) {
        return availableInvokers.get(ThreadLocalRandom.current().nextInt(availableInvokers.size()));
    }
}
