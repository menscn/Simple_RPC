package com.msc.rpc.invocation.callback;

import com.msc.rpc.common.context.RPCThreadSharedContext;
import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.config.ReferenceConfig;
import com.msc.rpc.config.ServiceConfig;
import com.msc.rpc.invocation.api.support.AbstractInvocation;

import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.invocation.callback
 * @Description: 回调调用
 */
public class CallbackInvocation extends AbstractInvocation {
    @Override
    protected RPCResponse doInvoke(RPCRequest request, ReferenceConfig referenceConfig, Function<RPCRequest, Future<RPCResponse>> requestProcessor) throws Throwable {
        Object callbackInstance = request.getParameters()[referenceConfig.getCallbackParamIndex()];
        request.getParameters()[referenceConfig.getCallbackParamIndex()] = null;
        registryCallbackHandler(request, referenceConfig, callbackInstance);
        requestProcessor.apply(request);

        //非同步调用,返回null
        return null;
    }

    /**
     * @return void
     * @Author mSc
     * @Description callback注册函数, 将callback实例注册到一个线程共享的全局Map中, 回调线程根据RPCRequestID取callback实例来执行
     * @Param [request, referenceConfig, callback]
     **/
    public void registryCallbackHandler(RPCRequest request, ReferenceConfig referenceConfig, Object callback) {
        Class<?> interfaceClass = callback.getClass().getInterfaces()[0];

        ServiceConfig serviceConfig = ServiceConfig.builder()
                .interfaceClass((Class<Object>) interfaceClass)
                .interfaceName(interfaceClass.getName())
                .isCallback(true)
                .ref(callback)
                .build();
        RPCThreadSharedContext.registryCallbackHandler(generateCallbackHandlerKey(request, referenceConfig), serviceConfig);
    }

    public static String generateCallbackHandlerKey(RPCRequest request, ReferenceConfig referenceConfig) {
        return request.getRequestID() + "." + request.getParameterTypes()[referenceConfig.getCallbackParamIndex()];
    }

    public static String generateCallbackHandlerKey(RPCRequest request) {
        return request.getRequestID() + "." + request.getInterfaceName();
    }

}
