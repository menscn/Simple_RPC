package com.msc.rpc.cluster.loadBalance;

import com.msc.rpc.cluster.api.support.AbstractLoadBalancer;
import com.msc.rpc.common.context.RPCStatus;
import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.protocol.api.Invoker;

import java.util.List;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.cluster.loadBalance
 * @Description: 最小活跃度负载均衡算法
 */
public class LeastActiveLoadBalancer extends AbstractLoadBalancer {
    /**
     * @return com.msc.rpc.protocol.api.Invoker
     * @Author mSc
     * @Description 选择可用节点中活跃度最小的
     * @Param [availableInvokers, request]
     **/
    @Override
    protected Invoker doSelect(List<Invoker> availableInvokers, RPCRequest request) {
        String interfaceName = request.getInterfaceName();
        String methodName = request.getMethodName();
        Invoker target = null;
        int leastActivity = 0;
        for (Invoker invoker : availableInvokers) {
            int curActivity = RPCStatus.getActivity(
                    interfaceName,
                    methodName,
                    invoker.getServiceURL().getServiceAddress());
            if (target == null || curActivity < leastActivity) {
                target = invoker;
                leastActivity = curActivity;
            }
        }
        return target;
    }
}
