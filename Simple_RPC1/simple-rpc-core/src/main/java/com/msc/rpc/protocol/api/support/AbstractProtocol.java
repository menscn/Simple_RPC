package com.msc.rpc.protocol.api.support;

import com.msc.rpc.common.enumeration.ExceptionEnum;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.config.GlobalConfig;
import com.msc.rpc.config.ServiceConfig;
import com.msc.rpc.protocol.api.Exporter;
import com.msc.rpc.protocol.api.Protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.protocol.api.support
 * @Description: 服务暴露，通过线程安全的map实现
 */
public abstract class AbstractProtocol implements Protocol {
    private Map<String, Exporter<?>> exporterMap = new ConcurrentHashMap<>();

    private GlobalConfig globalConfig;

    public void init(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    protected GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    /**
     * @return void
     * @Author mSc
     * @Description 将暴露的服务放入Map中存储
     * @Param [interfaceClass, exporter]
     **/
    public void putExporterMap(Class<?> interfaceClass, Exporter exporter) {
        exporterMap.put(interfaceClass.getName(), exporter);
    }

    /**
     * @return com.msc.rpc.config.ServiceConfig<T>
     * @Author mSc
     * @Description 参考本地服务
     * @Param [interfaceName]
     **/
    @Override
    public <T> ServiceConfig<T> referLocalService(String interfaceName) {
        if (!exporterMap.containsKey(interfaceName)) {
            //该服务未暴露出来
            throw new RPCException(ExceptionEnum.SERVER_NOT_EXPORTED, "SERVER_NOT_EXPORTED");
        }
        return (ServiceConfig<T>) exporterMap.get(interfaceName).getServiceConfig();
    }
}
