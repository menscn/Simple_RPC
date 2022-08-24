package com.msc.rpc.invocation.oneway;

import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.config.ReferenceConfig;
import com.msc.rpc.invocation.api.support.AbstractInvocation;

import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.invocation.oneway
 * @Description: oneWay方式调用, 即调用方不关心调用结果, 提交完请求就直接返回null
 */
public class OneWayInvocation extends AbstractInvocation {
    @Override
    protected RPCResponse doInvoke(RPCRequest request, ReferenceConfig referenceConfig, Function<RPCRequest, Future<RPCResponse>> requestProcessor) throws Throwable {
        requestProcessor.apply(request);

        //非同步调用,返回null
        return null;
    }
}