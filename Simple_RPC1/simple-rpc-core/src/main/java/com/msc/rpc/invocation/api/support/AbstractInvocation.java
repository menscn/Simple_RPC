package com.msc.rpc.invocation.api.support;

import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.common.enumeration.ExceptionEnum;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.config.ReferenceConfig;
import com.msc.rpc.invocation.api.Invocation;
import com.msc.rpc.protocol.api.InvokeParam;
import com.msc.rpc.protocol.api.support.RPCInvokeParam;

import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.invocation.api.support
 * @Description: 实现调用，但需要再次继承，确定最终调用方式
 */
public abstract class AbstractInvocation implements Invocation {
    /**
     * @return com.msc.rpc.common.domain.RPCResponse
     * @Author mSc
     * @Description 处理一些共性问题，具体实现需要重写doInvoke
     * @Param [invokeParam, requestProcessor]
     **/
    @Override
    public RPCResponse invoke(InvokeParam invokeParam, Function<RPCRequest, Future<RPCResponse>> requestProcessor) throws RPCException {
        RPCResponse response;
        RPCRequest request = ((RPCInvokeParam) invokeParam).getRpcRequest();
        ReferenceConfig referenceConfig = ((RPCInvokeParam) invokeParam).getReferenceConfig();
        try {
            response = doInvoke(request, referenceConfig, requestProcessor);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RPCException(ExceptionEnum.TRANSPORT_FAILURE, "TRANSPORT_FAILURE");
        }

        return response;
    }

    //具体的调用方法
    protected abstract RPCResponse doInvoke(RPCRequest request, ReferenceConfig referenceConfig, Function<RPCRequest, Future<RPCResponse>> requestProcessor) throws Throwable;
}

