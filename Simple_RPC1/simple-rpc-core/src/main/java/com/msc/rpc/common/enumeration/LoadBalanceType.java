package com.msc.rpc.common.enumeration;

import com.msc.rpc.cluster.api.LoadBalancer;
import com.msc.rpc.cluster.loadBalance.*;
import com.msc.rpc.common.enumeration.support.ExtensionBaseType;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.enumeration
 * @Description: 负载均衡方式选择
 */
public enum LoadBalanceType implements ExtensionBaseType<LoadBalancer> {
    CONSISTENTHASH(new ConsistentHashLoadBalancer()),    //一致性哈希
    LEASTACTIVE(new LeastActiveLoadBalancer()),          //最小活跃度
    RANDOM(new RandomLoadBalancer()),                    //随机
    WEIGHTEDRANDOM(new WeightedRandomLoadBalancer()),    //加权随机
    ROUNDROBIN(new RoundRobinLoadBalancer());            //轮询

    private LoadBalancer loadBalancer;

    /**
     * @return
     * @Author mSc
     * @Description 获取设置的负载均衡算法
     * @Param [loadBalancer]
     **/
    LoadBalanceType(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    @Override
    public LoadBalancer getInstance() {
        return loadBalancer;
    }
}