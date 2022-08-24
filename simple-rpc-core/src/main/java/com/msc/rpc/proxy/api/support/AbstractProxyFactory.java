package com.msc.rpc.proxy.api.support;

import com.msc.rpc.common.domain.GlobalRecycler;
import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.config.ReferenceConfig;
import com.msc.rpc.protocol.api.InvokeParam;
import com.msc.rpc.protocol.api.Invoker;
import com.msc.rpc.protocol.api.support.AbstractInvoker;
import com.msc.rpc.protocol.api.support.RPCInvokeParam;
import com.msc.rpc.proxy.api.RPCProxyFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.proxy.api.support
 * @Description: 实现动态代理工厂
 */
public abstract class AbstractProxyFactory implements RPCProxyFactory {

    private Map<Class<?>, Object> CACHE = new ConcurrentHashMap<>();

    /**
     * @return T
     * @Author mSc
     * @Description 缓存中存在，则直接返回；不存在则创建，并加入到缓存中
     * @Param [invoker]
     **/
    @Override
    @SuppressWarnings("unchecked")
    public <T> T createProxy(Invoker<T> invoker) {
        if (CACHE.containsKey(invoker.getInterfaceClass())) {
            return (T) CACHE.get(invoker.getInterfaceClass());
        }

        T proxy = doCreateProxy(invoker.getInterfaceClass(), invoker);

        CACHE.put(invoker.getInterfaceClass(), proxy);

        return proxy;
    }

    /**
     * @return T
     * @Author mSc
     * @Description 具体实现待子类写
     * @Param [InterfaceClass, invoker]
     **/
    protected abstract <T> T doCreateProxy(Class<T> InterfaceClass, Invoker<T> invoker);

    /**
     * @return java.lang.Object
     * @Author mSc
     * @Description 主要获取参数
     * @Param [invoker, method, args]
     **/
    protected Object invokeProxyMethod(Invoker invoker, Method method, Object[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] paramTypes = new String[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            paramTypes[i] = parameterTypes[i].getTypeName();
        }
        return invokeProxyMethod(invoker, method.getDeclaringClass().getName(), method.getName(), paramTypes, args);
    }

    /**
     * @return java.lang.Object
     * @Author mSc
     * @Description 通过方法，接口名，参数等获取代理类
     * @Param [invoker, interfaceName, methodName, paramTypes, args]
     **/
    protected Object invokeProxyMethod(Invoker invoker, String interfaceName, String methodName, String[] paramTypes, Object[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if ("toString".equals(methodName) && paramTypes.length == 0) {
            return invoker.toString();
        }
        if ("hashCode".equals(methodName) && paramTypes.length == 0) {
            return invoker.hashCode();
        }
        if ("equals".equals(methodName) && paramTypes.length == 1) {
            return invoker.equals(args[0]);
        }
/**
 * 进行调用，通过respon获取结果
 */
        RPCRequest request = GlobalRecycler.reuse(RPCRequest.class);
        request.setRequestID(UUID.randomUUID().toString());
        request.setParameters(args);
        request.setParameterTypes(paramTypes);
        request.setInterfaceName(interfaceName);
        request.setMethodName(methodName);
        RPCInvokeParam invokeParam = RPCInvokeParam.builder()
                .rpcRequest(request)
                .referenceConfig(ReferenceConfig.getReferenceConfigByInterfaceName(interfaceName))
                .build();
        RPCResponse response = invoker.invoke(invokeParam);
        Object result = null;
        //若response == null，则属于异步调用：callback、oneway、async
        if (response != null) {
            //同步调用
            result = response.getResult();
        }
        response.recyle();
        return result;
    }

    /**
     * @return com.msc.rpc.protocol.api.Invoker<T>
     * @Author mSc
     * @Description 通过代理类获取Invoker
     * @Param [proxy, type]
     **/
    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type) {
        return new AbstractInvoker<T>() {
            @Override
            public Class<T> getInterfaceClass() {
                return type;
            }

            @Override
            public String getInterfaceName() {
                return type.getName();
            }

            @Override
            public RPCResponse invoke(InvokeParam invokeParam) throws RPCException {
                RPCResponse response = GlobalRecycler.reuse(RPCResponse.class);
                try {
                    response.setRequestID(invokeParam.getRequestID());
                    Method method = proxy.getClass().getMethod(invokeParam.getMethodName(), invokeParam.getParameterTypes());
                    response.setResult(method.invoke(proxy, invokeParam.getParameter()));
                } catch (Exception e) {
                    response.setErrorCause(e);
                }
                return response;
            }
        };
    }
}