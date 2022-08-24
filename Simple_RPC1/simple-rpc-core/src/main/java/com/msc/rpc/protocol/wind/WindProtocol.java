package com.msc.rpc.protocol.wind;

import com.msc.rpc.client.filter.Filter;
import com.msc.rpc.common.enumeration.ExceptionEnum;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.config.ReferenceConfig;
import com.msc.rpc.config.ServiceConfig;
import com.msc.rpc.protocol.api.Exporter;
import com.msc.rpc.protocol.api.Invoker;
import com.msc.rpc.protocol.api.support.AbstractRemoteProtocol;
import com.msc.rpc.registry.api.ServiceURL;
import com.msc.rpc.transport.api.Client;
import com.msc.rpc.transport.api.Server;
import com.msc.rpc.transport.server.WindServer;
import com.msc.rpc.transport.wind.client.WindClient;

import java.util.List;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.protocol.wind
 * @Description: Wind协议
 */
public class WindProtocol extends AbstractRemoteProtocol {

    /**
     * @return com.msc.rpc.transport.api.Client
     * @Author mSc
     * @Description 目标服务器未与本机相连的时候, 由具体的protocol实例来调用该方法创建连接，并返回client客户端
     * @Param [serviceURL]
     **/
    @Override
    protected Client doInitClient(ServiceURL serviceURL) {
        WindClient windClient = new WindClient();
        windClient.init(serviceURL, getGlobalConfig());
        return windClient;
    }

    /**
     * @return com.msc.rpc.transport.api.Server
     * @Author mSc
     * @Description 开启服务端
     * @Param []
     **/
    @Override
    protected Server doOpenServer() {
        WindServer windServer = new WindServer();
        windServer.init(getGlobalConfig());
        windServer.run();
        return windServer;
    }

    /**
     * @return com.msc.rpc.protocol.api.Invoker<T>
     * @Author mSc
     * @Description 将过滤器引入期中，构造windInvoker
     * @Param [serviceURL, referenceConfig]
     **/
    @Override
    public <T> Invoker<T> refer(ServiceURL serviceURL, ReferenceConfig referenceConfig) {
        //创建了一个面向Wind协议的调用者,负责提供getProcessor()方法与transport层交互
        WindInvoker<T> windInvoker = new WindInvoker<>();
        windInvoker.setInterfaceName(referenceConfig.getInterfaceName());
        windInvoker.setInterfaceClass(referenceConfig.getInterfaceClass());
        windInvoker.setGlobalConfig(getGlobalConfig());
        windInvoker.setClient(initClient(serviceURL));
        List<Filter> filters = referenceConfig.getFilters();
        if (filters.size() == 0) {
            return windInvoker;
        }

        return windInvoker.buildFilterChain(filters);
    }

    /**
     * @return com.msc.rpc.protocol.api.Exporter<T>
     * @Author mSc
     * @Description 将Wind暴露出来
     * @Param [localInvoker, serviceConfig]
     **/
    @Override
    public <T> Exporter<T> export(Invoker localInvoker, ServiceConfig serviceConfig) {
        WindExporter<T> windExporter = new WindExporter<>();
        windExporter.setInvoker(localInvoker);
        windExporter.setServiceConfig(serviceConfig);

        putExporterMap(localInvoker.getInterfaceClass(), windExporter);
        //暴露服务之前先打开服务连接，防止暴露服务之后,还没打开服务连接,
        //就已经有客户端从注册中心获取了该服务地址并开始尝试建立连接
        openServer();
        try {
            serviceConfig.getRegistryConfig().getRegistryInstance().register(
                    //主机IP+端口号
                    "192.168.1.116" + ":" + getGlobalConfig().getPort(),
                    localInvoker.getInterfaceName(),
                    localInvoker.getInterfaceClass());
        } catch (Exception e) {
            throw new RPCException(ExceptionEnum.FAIL_TO_GET_LOCALHOST_ADDRESS, "FAIL_TO_GET_LOCALHOST_ADDRESS");
        }
        return windExporter;
    }
}
