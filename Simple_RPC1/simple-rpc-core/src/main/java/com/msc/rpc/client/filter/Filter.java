package com.msc.rpc.client.filter;

import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.protocol.api.InvokeParam;
import com.msc.rpc.protocol.api.Invoker;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.client.filter
 * @Description: 过滤器接口
 */
public interface Filter {
    /**
     * @return com.msc.rpc.common.domain.RPCResponse
     * @Author mSc
     * @Description 调用方法，返回RPC响应信息
     * @Param [invoker, invokeParam]
     **/
    RPCResponse invoke(Invoker invoker, InvokeParam invokeParam);
}
