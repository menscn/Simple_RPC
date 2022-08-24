package com.msc.rpc.config;

import com.msc.rpc.cluster.api.FaultToleranceHandler;
import com.msc.rpc.cluster.api.LoadBalancer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.config
 * @Description: 集群配置类
 * 1）负载均衡策略：随机、加权随机、轮询、一致性哈希、最小活跃度
 * 2）集群容错策略：FailOver、FailFast、FailSafe
 * 3）负载均衡器实例
 * 4）集群容错处理器实例
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClusterConfig {
    private String loadbalance;
    private String faulttolerance;
    private LoadBalancer loadBalanceInstance;
    private FaultToleranceHandler faultToleranceHandlerInstance;
}
