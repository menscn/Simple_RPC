package com.msc.rpc.config;

import com.msc.rpc.proxy.api.RPCProxyFactory;
import com.msc.rpc.serialize.api.Serializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.config
 * @Description: 项目应用配置类，主要包括-》
 * 1）应用名称
 * 2）序列化算法：Jdk、Protostuff
 * 3）代理对象工厂
 * 4）序列化算法实例
 * 5）代理对象工厂实例
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationConfig {
    private String name;
    private String serializer;
    private String proxyFactoryName;

    private Serializer serializerInstance;
    private RPCProxyFactory proxyFactoryInstance;
}
