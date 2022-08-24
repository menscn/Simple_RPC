package com.msc.rpc.protocol.api;

import com.msc.rpc.config.ServiceConfig;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.protocol.api
 * @Description: 服务暴露之后的抽象调用接口:实质上就是将Invoker和ServiceConfig进行了封装
 */
public interface Exporter<T> {
    Invoker<T> getInvoker();

    ServiceConfig<T> getServiceConfig();
}
