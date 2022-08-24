package com.msc.rpc.invocation.api;

import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.protocol.api.InvokeParam;

import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.invocation.api
 * @Description: 远程调用方式的接口, 具体有四种实现方式:
 * 1)同步调用
 * 2)异步调用
 * 3)oneWay调用
 * 4)回调调用
 */
public interface Invocation {
    RPCResponse invoke(InvokeParam invokeParam, Function<RPCRequest, Future<RPCResponse>> requestProcessor) throws RPCException;
}

