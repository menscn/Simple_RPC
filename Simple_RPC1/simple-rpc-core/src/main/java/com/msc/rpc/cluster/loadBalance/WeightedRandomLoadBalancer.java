package com.msc.rpc.cluster.loadBalance;

import com.msc.rpc.cluster.api.support.AbstractLoadBalancer;
import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.protocol.api.Invoker;
import com.msc.rpc.registry.api.ServiceURL;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.cluster.loadBalance
 * @Description: 加权随机负载均衡算法
 */
public class WeightedRandomLoadBalancer extends AbstractLoadBalancer {
    /**
     * @return com.msc.rpc.protocol.api.Invoker
     * @Author mSc
     * @Description 相比于随机负载均衡，算是能者多劳
     * @Param [availableInvokers, request]
     **/
    @Override
    protected Invoker doSelect(List<Invoker> availableInvokers, RPCRequest request) {
        int weightSum = 0; //权重和
        //计算权重
        for (Invoker invoker : availableInvokers) {
            weightSum += Integer.parseInt(invoker.getServiceURL().getParamsByKey(ServiceURL.Key.WEIGHT).get(0));
        }
        int randomValue = ThreadLocalRandom.current().nextInt(weightSum);
        for (Invoker invoker : availableInvokers) {
            randomValue -= Integer.parseInt(invoker.getServiceURL().getParamsByKey(ServiceURL.Key.WEIGHT).get(0));
            if (randomValue < 0) {
                return invoker;
            }
        }
        return null;
    }
}
