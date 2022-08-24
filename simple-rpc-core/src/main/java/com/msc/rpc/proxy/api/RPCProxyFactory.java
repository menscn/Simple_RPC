package com.msc.rpc.proxy.api;

import com.msc.rpc.protocol.api.Invoker;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.proxy.api
 * @Description: RPC动态代理工厂
 */
public interface RPCProxyFactory {

    <T> T createProxy(Invoker<T> invoker);

    <T> Invoker<T> getInvoker(T proxy, Class<T> type);
}
