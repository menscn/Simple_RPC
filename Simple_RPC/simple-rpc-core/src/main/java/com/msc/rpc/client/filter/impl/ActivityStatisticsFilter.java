package com.msc.rpc.client.filter.impl;

import com.msc.rpc.client.filter.Filter;
import com.msc.rpc.common.context.RPCStatus;
import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.protocol.api.InvokeParam;
import com.msc.rpc.protocol.api.Invoker;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.client.filter.impl
 * @Description: 开始调用活跃度+1，若失败活跃度-1，顺利调用完成-1
 */
@Slf4j
public class ActivityStatisticsFilter implements Filter {
    @Override
    public RPCResponse invoke(Invoker invoker, InvokeParam invokeParam) {
        RPCResponse response = null;
        try {
            log.info("start RPC...");
            //调用开始,活跃度+1
            RPCStatus.inActivity(
                    invoker.getInterfaceName(),
                    invokeParam.getMethodName(),
                    invoker.getServiceURL().getServiceAddress());
            response = invoker.invoke(invokeParam);  //调用下一个filter,或者真正的invoker
        } catch (RPCException e) {
            log.info("Catch a RPC Exception...");
            //调用失败, 活跃度-1
            RPCStatus.deActivity(
                    invoker.getInterfaceName(),
                    invokeParam.getMethodName(),
                    invoker.getServiceURL().getServiceAddress());
            throw e;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用顺利完成,活跃度-1
        RPCStatus.deActivity(
                invoker.getInterfaceName(),
                invokeParam.getMethodName(),
                invoker.getServiceURL().getServiceAddress());

        return response;
    }
}

