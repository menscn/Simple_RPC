package com.msc.rpc.transport.api.support;

import com.msc.rpc.config.GlobalConfig;
import com.msc.rpc.transport.api.Server;


/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.transport.api.support
 * @Description: 抽象服务端
 */
public abstract class AbstractServer implements Server {
    private GlobalConfig globalConfig;

    /**
     * @return void
     * @Author mSc
     * @Description 具体怎么初始化由实现类决定
     * @Param [globalConfig]
     **/
    public void init(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        doInit();
    }

    protected GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    protected abstract void doInit();
}