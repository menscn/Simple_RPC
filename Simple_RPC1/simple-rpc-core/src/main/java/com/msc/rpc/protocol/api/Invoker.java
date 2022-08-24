package com.msc.rpc.protocol.api;

import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.registry.api.ServiceURL;

import java.lang.reflect.InvocationTargetException;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.protocol.api
 * @Description: 调用者协议接口规范
 */
public interface Invoker<T> {
    Class<T> getInterfaceClass();

    String getInterfaceName();

    ServiceURL getServiceURL();

    /**
     * @return com.msc.rpc.common.domain.RPCResponse
     * @Author mSc
     * @Description 传入调用者的参数，返回按照RPCResponse格式
     * @Param [invokeParam]
     **/
    RPCResponse invoke(InvokeParam invokeParam) throws RPCException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    /**
     * @return boolean
     * @Author mSc
     * @Description 判断调用者是否可用
     * @Param []
     **/
    boolean isAvailable();
}
