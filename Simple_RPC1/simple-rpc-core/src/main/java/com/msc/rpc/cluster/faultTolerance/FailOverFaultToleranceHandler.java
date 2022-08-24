package com.msc.rpc.cluster.faultTolerance;

import com.msc.rpc.cluster.api.FaultToleranceHandler;
import com.msc.rpc.cluster.api.support.ClusterInvoker;
import com.msc.rpc.common.constant.ClusterConstant;
import com.msc.rpc.common.context.RPCThreadLocalContext;
import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.common.enumeration.ExceptionEnum;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.protocol.api.InvokeParam;
import com.msc.rpc.protocol.api.Invoker;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.cluster.faultTolerance
 * @Description: 集群容错机制:自动切换
 * RPC调用在一台服务器上出现异常后,快速将请求切换到别的服务器上,通常用于读操作
 */
@Slf4j
public class FailOverFaultToleranceHandler implements FaultToleranceHandler {
    /**
     * @return com.msc.rpc.common.domain.RPCResponse
     * @Author mSc
     * @Description 删除不可用的invoker，调用失败自动切换写一个，在允许的重试次数中且还有可以用的服务，会一直重复这个过程
     * @Param [clusterInvoker, invokeParam, e]
     **/
    @Override
    public RPCResponse handle(ClusterInvoker clusterInvoker, InvokeParam invokeParam, RPCException e) {
        //取出当前线程对应的Invoker,这是出现异常的Invoker,需从服务列表中删除
        Invoker failInvoker = RPCThreadLocalContext.getContext().getInvoker();
        Map<String, Invoker> excludeInvokers = new HashMap<>();
        excludeInvokers.put(failInvoker.getServiceURL().getServiceAddress(), failInvoker);
        for (int i = 0; i < ClusterConstant.FAILOVER_RETRY_TIMES; i++) {
            List<Invoker> availableInvokers = clusterInvoker.getAvailableInvokers();
            Iterator<Invoker> iter = availableInvokers.iterator();
            while (iter.hasNext()) {
                if (excludeInvokers.containsKey(iter.next().getServiceURL().getServiceAddress())) {
                    iter.remove();
                }
            }

            if (availableInvokers.size() == 0) {
                //没有可用服务了
                e.printStackTrace();
                throw new RPCException(ExceptionEnum.NO_AVAILABLE_SERVICE, "NO_AVAILABLE_SERVICE");
            }

            try {
                log.info("正在重试......");
                return clusterInvoker.invokeForFaultTolerance(availableInvokers, invokeParam);
            } catch (Exception e1) {
                e1.printStackTrace();
                log.info("第{}次重试失败...", i + 1);
                Invoker fInvoker = RPCThreadLocalContext.getContext().getInvoker();
                //将失败的Invoker放入失败队列
                excludeInvokers.put(fInvoker.getServiceURL().getServiceAddress(), fInvoker);
            }
        }
        //指定次数内,依然没有成功
        throw new RPCException(ExceptionEnum.EXCEEDED_RETRIES, "EXCEEDED_RETRIES");
    }
}
