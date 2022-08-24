package com.msc.rpc.proxy.jdk;

import com.msc.rpc.protocol.api.Invoker;
import com.msc.rpc.proxy.api.support.AbstractProxyFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.proxy.jdk
 * @Description: 通过JDK的方式获取代理类
 */
public class JDKRPCProxyFactory extends AbstractProxyFactory {
    @Override
    @SuppressWarnings("unchecked")
    protected <T> T doCreateProxy(Class<T> interfaceClass, Invoker<T> invoker) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return JDKRPCProxyFactory.this.invokeProxyMethod(invoker, method, args);
                    }
                });
    }
}