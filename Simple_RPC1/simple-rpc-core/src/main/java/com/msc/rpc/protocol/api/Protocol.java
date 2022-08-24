package com.msc.rpc.protocol.api;

import com.msc.rpc.config.ReferenceConfig;
import com.msc.rpc.config.ServiceConfig;
import com.msc.rpc.registry.api.ServiceURL;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.protocol.api
 * @Description: 定义协议
 */
public interface Protocol {
    <T> Invoker<T> refer(ServiceURL serviceURL, ReferenceConfig referenceConfig);

    <T> Exporter<T> export(Invoker localInvoker, ServiceConfig serviceConfig);

    <T> ServiceConfig<T> referLocalService(String interfaceName);

    void close() throws InterruptedException;
}
