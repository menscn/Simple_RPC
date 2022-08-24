package com.msc.rpc.protocol.api;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.protocol.api
 * @Description: 服务参数接口:定义了调用一个服务需要哪些参数
 */
public interface InvokeParam {
    String getInterfaceName();

    String getMethodName();

    Class<?>[] getParameterTypes();

    Object[] getParameter();

    String getRequestID();
}
