package com.msc.rpc.cluster.loadBalance;

import com.msc.rpc.cluster.api.support.AbstractLoadBalancer;
import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.protocol.api.Invoker;

import java.util.List;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.cluster.loadBalance
 * @Description: 轮询负载均衡算法
 */
public class RoundRobinLoadBalancer extends AbstractLoadBalancer {
    private int index = 0;  //轮询负载均衡算法中,当前应当被选择的服务器索引

    /**
     * @return com.msc.rpc.protocol.api.Invoker
     * @Author mSc
     * @Description 轮询算法按顺序把每个新的连接请求分配给下一个服务器 -> 平分
     * @Param [availableInvokers, request]
     **/
    @Override
    protected Invoker doSelect(List<Invoker> availableInvokers, RPCRequest request) {
        //定义为同步方法,保证index的正确性,防止多线程同时请求轮询时并发更新index
        Invoker invoker = availableInvokers.get(index);
        index = (index + 1) % availableInvokers.size();
        return invoker;
    }
}