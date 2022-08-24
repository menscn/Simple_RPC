package com.msc.rpc.config;

import com.msc.rpc.registry.api.ServiceRegistry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.config
 * @Description: 注册中心配置类，主要配置以下内容：
 * 1）注册中心类型
 * 2）注册中心地址
 * 3）注册中心实例
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistryConfig {
    private String type; // 注册中心类型
    private String address; // 注册中心地址
    private ServiceRegistry registryInstance; //注册中心实例

    /**
     * @return void
     * @Author mSc
     * @Description 初始化注册中心
     * @Param []
     **/
    public void init() {
        if (registryInstance != null) {
            registryInstance.init();
        }
    }

    /**
     * @return void
     * @Author mSc
     * @Description 关闭注册中心实例
     * @Param []
     **/
    public void close() throws InterruptedException {
        if (registryInstance != null) {
            registryInstance.close();
        }
    }
}
