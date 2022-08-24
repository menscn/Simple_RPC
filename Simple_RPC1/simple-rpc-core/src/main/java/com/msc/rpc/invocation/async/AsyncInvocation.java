package com.msc.rpc.invocation.async;

import com.msc.rpc.common.context.RPCThreadLocalContext;
import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.config.ReferenceConfig;
import com.msc.rpc.invocation.api.support.AbstractInvocation;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.invocation.async
 * @Description: 异步调用
 */
public class AsyncInvocation extends AbstractInvocation {
    @Override
    protected RPCResponse doInvoke(RPCRequest request, ReferenceConfig referenceConfig, Function<RPCRequest, Future<RPCResponse>> requestProcessor) throws Throwable {
        Future<RPCResponse> future = requestProcessor.apply(request);

        Future<Object> requestFuture = new Future<Object>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return future.cancel(mayInterruptIfRunning);
            }

            @Override
            public boolean isCancelled() {
                return future.isCancelled();
            }

            @Override
            public boolean isDone() {
                return future.isDone();
            }

            @Override
            public Object get() throws InterruptedException, ExecutionException {
                RPCResponse response = future.get();
                if (response.hasError()) {
                    throw new ExecutionException(response.getErrorCause());
                }
                return response.getResult();
            }

            @Override
            public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException, TimeoutException {
                RPCResponse response = future.get(timeout, unit);
                if (response.hasError()) {
                    throw new ExecutionException(response.getErrorCause());
                }

                return response.getResult();
            }
        };
        RPCThreadLocalContext.getContext().setFuture(requestFuture);

        //非同步调用,返回null
        return null;
    }
}
