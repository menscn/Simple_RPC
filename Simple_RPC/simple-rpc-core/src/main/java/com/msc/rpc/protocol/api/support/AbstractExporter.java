package com.msc.rpc.protocol.api.support;

import com.msc.rpc.config.ServiceConfig;
import com.msc.rpc.protocol.api.Exporter;
import com.msc.rpc.protocol.api.Invoker;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.protocol.api.support
 * @Description: 将invoker与serviceConfig进行封装
 */
public abstract class AbstractExporter<T> implements Exporter<T> {
    private Invoker<T> invoker;

    private ServiceConfig serviceConfig;

    @Override
    public Invoker<T> getInvoker() {
        return invoker;
    }

    @Override
    public ServiceConfig<T> getServiceConfig() {
        return serviceConfig;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    public void setServiceConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }
}
