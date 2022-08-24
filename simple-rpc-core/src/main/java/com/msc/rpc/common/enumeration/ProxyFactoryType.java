package com.msc.rpc.common.enumeration;

import com.msc.rpc.common.enumeration.support.ExtensionBaseType;
import com.msc.rpc.proxy.api.RPCProxyFactory;
import com.msc.rpc.proxy.jdk.JDKRPCProxyFactory;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.enumeration
 * @Description: 获得代理工厂类型
 * 目前只有JDK自带的动态代理工厂
 */
public enum ProxyFactoryType implements ExtensionBaseType<RPCProxyFactory> {
    JDK(new JDKRPCProxyFactory());  //jdk实现的动态代理工厂

    private RPCProxyFactory rpcProxyFactory;

    ProxyFactoryType(RPCProxyFactory rpcProxyFactory) {
        this.rpcProxyFactory = rpcProxyFactory;
    }

    @Override
    public RPCProxyFactory getInstance() {
        return rpcProxyFactory;
    }
}

