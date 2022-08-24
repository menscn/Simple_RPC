package com.msc.rpc.protocol.api.support;

import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.protocol.api.InvokeParam;
import com.msc.rpc.protocol.api.Invoker;

import java.lang.reflect.InvocationTargetException;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.protocol.api.support
 * @Description: 默认的invoker
 */
public abstract class DelegateInvoker<T> extends AbstractInvoker<T> {
    private Invoker<T> delegate;

    public DelegateInvoker(Invoker<T> invoker) {
        this.delegate = invoker;
    }

    public Invoker<T> getDelegate() {
        return delegate;
    }

    public abstract RPCResponse invoke(InvokeParam invokeParam) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
}
