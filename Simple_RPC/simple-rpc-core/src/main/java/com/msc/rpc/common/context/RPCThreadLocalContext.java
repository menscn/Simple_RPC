package com.msc.rpc.common.context;

import com.msc.rpc.protocol.api.Invoker;

import java.util.concurrent.Future;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.context
 * @Description: 客户端、服务端本地的上下文
 */
public class RPCThreadLocalContext {
    private static final ThreadLocal<RPCThreadLocalContext> RPC_CONTEXT = new ThreadLocal<RPCThreadLocalContext>() {
        @Override
        protected RPCThreadLocalContext initialValue() {
            return new RPCThreadLocalContext();
        }
    };

    /**
     * @return com.msc.rpc.common.context.RPCThreadLocalContext
     * @Author mSc
     * @Description 从threadLocal中取出当前线程对应的RPCThreadLocalContext对象
     * @Param []
     **/
    public static RPCThreadLocalContext getContext() {
        return RPC_CONTEXT.get();
    }

    /**
     * Invoker是Dubbo中的实体域
     * 代表一个可执行体，可向它发起invoke调用。在服务提供方，Invoker用于调用服务提供类。在服务消费方，Invoker用于执行远程调用
     */
    private Invoker invoker;
    /**
     * 接受多线程运行的结果
     */
    private Future future;

    public Invoker getInvoker() {
        return invoker;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    public Future getFuture() {
        return future;
    }

    public void setFuture(Future future) {
        this.future = future;
    }
}
