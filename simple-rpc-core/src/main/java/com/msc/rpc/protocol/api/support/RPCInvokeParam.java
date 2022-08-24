package com.msc.rpc.protocol.api.support;

import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.config.ReferenceConfig;
import com.msc.rpc.protocol.api.InvokeParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.protocol.api.support
 * @Description: PRC远程调用需要的参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RPCInvokeParam implements InvokeParam {
    private RPCRequest rpcRequest;

    private ReferenceConfig referenceConfig;

    @Override
    public String getInterfaceName() {
        return rpcRequest.getInterfaceName();
    }

    @Override
    public String getMethodName() {
        return rpcRequest.getMethodName();
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return rpcRequest.getParameterTypes();
    }

    @Override
    public Object[] getParameter() {
        return rpcRequest.getParameters();
    }

    @Override
    public String getRequestID() {
        return rpcRequest.getRequestID();
    }

    public String ToString() {
        return "RPCInvokeParam{" +
                rpcRequest +
                "}";
    }
}
