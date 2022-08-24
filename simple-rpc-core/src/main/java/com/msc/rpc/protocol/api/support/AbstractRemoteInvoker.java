package com.msc.rpc.protocol.api.support;

import com.msc.rpc.registry.api.ServiceURL;
import com.msc.rpc.transport.api.Client;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.protocol.api.support
 * @Description: 远程调用，客户端发起
 */
public class AbstractRemoteInvoker<T> extends AbstractInvoker<T> {

    //由于是远程调用,因此需要一个客户端实例
    private Client client;

    @Override
    public ServiceURL getServiceURL() {
        return getClient().getServiceURL();
    }

    protected Client getClient() {
        return client;
    }

    @Override
    public boolean isAvailable() {
        return getClient().isAvailable();
    }

    public void setClient(Client client) {
        this.client = client;
    }
}