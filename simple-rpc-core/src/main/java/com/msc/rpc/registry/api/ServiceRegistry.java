package com.msc.rpc.registry.api;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.registry.api
 * @Description: 服务注册中心
 * 定义了初始化注册中心、服务发现、服务注册、关闭注册中心等方法
 */
public interface ServiceRegistry {
    /**
     * @return void
     * @Author mSc
     * @Description 初始化
     * @Param []
     **/
    void init();

    /**
     * @return void
     * @Author mSc
     * @Description 服务发现，并更新注册中心服务列表，用于consumer方
     * @Param [interfaceName, serviceAddOrUpdateCallback, serviceOfflineCallback]
     **/
    void discover(String interfaceName, ServiceAddOrUpdateCallback serviceAddOrUpdateCallback, ServiceOfflineCallback serviceOfflineCallback);

    /**
     * @return void
     * @Author mSc
     * @Description 服务注册，用于provider方
     * @Param [serviceAddress, interfaceName, interfaceClass]
     **/
    void register(String serviceAddress, String interfaceName, Class<?> interfaceClass);

    /**
     * @return void
     * @Author mSc
     * @Description 关闭服务
     * @Param []
     **/
    void close() throws InterruptedException;
}
