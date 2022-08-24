package com.msc.rpc.transport.api.support;

import com.msc.rpc.config.GlobalConfig;
import com.msc.rpc.registry.api.ServiceURL;
import com.msc.rpc.transport.api.Client;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.transport.api.support
 * @Description: 客户端抽象类，实现了部分功能
 */
public abstract class AbstractClient implements Client {
    //为客户端提供服务地址
    private ServiceURL serviceURL;
    //提供用户配置信息
    private GlobalConfig globalConfig;

    /**
     * @return void
     * @Author mSc
     * @Description 客户端与服务端建立连接
     * @Param [serviceURL, globalConfig]
     **/
    public void init(ServiceURL serviceURL, GlobalConfig globalConfig) {
        this.serviceURL = serviceURL;
        this.globalConfig = globalConfig;
        //与远程节点建立连接
        connect();
    }

    //由具体的通信框架实现的方法:与远程服务器节点建立连接
    protected abstract void connect();

    @Override
    public void updateServiceConfig(ServiceURL serviceURL) {
        this.serviceURL = serviceURL;
    }

    @Override
    public ServiceURL getServiceURL() {
        return serviceURL;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }
}