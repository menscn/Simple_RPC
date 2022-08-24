package com.msc.rpc.config.supoport;

import com.msc.rpc.config.*;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.config
 * @Description: 获取配置类信息
 */
public class AbstractConfig {
    private GlobalConfig globalConfig;

    /**
     * @return void
     * @Author mSc
     * @Description 初始化全局配置类
     * @Param [globalConfig]
     **/
    public void init(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    /**
     * @return com.msc.rpc.config.ApplicationConfig
     * @Author mSc
     * @Description 通过全局配置获取应用配置类
     * @Param []
     **/
    public ApplicationConfig getApplicationConfig() {
        return globalConfig.getApplicationConfig();
    }

    /**
     * @return com.msc.rpc.config.ClusterConfig
     * @Author mSc
     * @Description 获取集群配置类
     * @Param []
     **/
    public ClusterConfig getClusterConfig() {
        return globalConfig.getClusterConfig();
    }

    /**
     * @return com.msc.rpc.config.ProtocolConfig
     * @Author mSc
     * @Description 获取协议配置信息
     * @Param []
     **/
    public ProtocolConfig getProtocolConfig() {
        return globalConfig.getProtocolConfig();
    }

    /**
     * @return com.msc.rpc.config.RegistryConfig
     * @Author mSc
     * @Description 获取注册配置信息
     * @Param []
     **/
    public RegistryConfig getRegistryConfig() {
        return globalConfig.getRegistryConfig();
    }
}
