package com.msc.rpc.protocol.wind;

import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.protocol.api.support.AbstractRemoteInvoker;

import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.protocol.wind
 * @Description: 远程调用具体实现
 */
public class WindInvoker<T> extends AbstractRemoteInvoker<T> {
    public Function<RPCRequest, Future<RPCResponse>> getProcessor() {
        //返回一个与传输层交互的函数
        return request -> getClient().submit(request);
    }
}
