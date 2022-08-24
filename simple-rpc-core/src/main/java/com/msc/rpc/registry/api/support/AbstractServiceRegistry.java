package com.msc.rpc.registry.api.support;

import com.msc.rpc.config.RegistryConfig;
import com.msc.rpc.registry.api.ServiceRegistry;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.registry.api.support
 * @Description: 抽象的服务注册中心
 * 只提供registryConfig配置对象
 */
public abstract class AbstractServiceRegistry implements ServiceRegistry {
    protected RegistryConfig registryConfig;

    /**
     * @return void
     * @Author mSc
     * @Description 设置注册配置
     * @Param [registryConfig]
     **/
    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }
}
