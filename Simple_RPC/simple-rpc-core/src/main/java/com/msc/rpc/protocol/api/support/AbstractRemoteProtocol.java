package com.msc.rpc.protocol.api.support;

import com.msc.rpc.common.enumeration.ExceptionEnum;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.registry.api.ServiceURL;
import com.msc.rpc.transport.api.Client;
import com.msc.rpc.transport.api.Server;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.protocol.api.support
 * @Description: 远程协议
 */
@Slf4j
public abstract class AbstractRemoteProtocol extends AbstractProtocol {
    /**
     * key:address
     * value:Client
     * 一个address对应一个client连接
     * 一条连接上的两个endPoint不论invoker是否相同,都共享连接,避免毫无意义地重复创建
     */
    private Map<String, Client> clientMap = new ConcurrentHashMap<>();

    /**
     * 锁Map
     * 以address创建锁对象
     * 同一时刻，两个目标address相同的Protocol只能有一个获取到该address的锁对象并进入临界区
     */
    private Map<String, Object> locks = new ConcurrentHashMap<>();

    private Server server;

    /**
     * @return com.msc.rpc.transport.api.Client
     * @Author mSc
     * @Description 初始化客户端
     * @Param [serviceURL]
     **/
    protected final Client initClient(ServiceURL serviceURL) {
        //目标服务器address
        String address = serviceURL.getServiceAddress();
        locks.putIfAbsent(address, new Object());
        synchronized (locks.get(address)) {
            if (clientMap.containsKey(address)) {
                return clientMap.get(address);
            }
            Client client = doInitClient(serviceURL);
            clientMap.put(address, client);
            return client;
        }
    }

    /**
     * @return com.msc.rpc.transport.api.Client
     * @Author mSc
     * @Description 通过URL获取Client
     * @Param [serviceURL]
     **/
    protected abstract Client doInitClient(ServiceURL serviceURL);

    /**
     * @return void
     * @Author mSc
     * @Description 更新端点配置
     * @Param [serviceURL]
     **/
    public final void updateEndpointConfig(ServiceURL serviceURL) {
        if (!clientMap.containsKey(serviceURL.getServiceAddress())) {
            throw new RPCException(ExceptionEnum.SERVER_ADDRESS_IS_NOT_CONFIGURATION, "SERVER_ADDRESS_IS_NOT_CONFIGURATION");
        }

        clientMap.get(serviceURL.getServiceAddress()).updateServiceConfig(serviceURL);
    }

    /**
     * @return void
     * @Author mSc
     * @Description 关闭客户端
     * @Param [address]
     **/
    public final void closeEndpoint(String address) {
        Client client = clientMap.remove(address);

        if (client != null) {
            log.info("正在关闭客户端..." + address);
            client.close();
        } else {
            log.error("请不要重复关闭客户端");
        }
    }

    /**
     * @return void
     * @Author mSc
     * @Description 开启服务
     * @Param []
     **/
    protected synchronized final void openServer() {
        if (server == null) {
            server = doOpenServer();
        }
    }

    /**
     * @return com.msc.rpc.transport.api.Server
     * @Author mSc
     * @Description 待实现类实现开启服务
     * @Param []
     **/
    protected abstract Server doOpenServer();

    @Override
    public void close() throws InterruptedException {
        clientMap.values().forEach(Client::close);
        if (server != null) {
            server.close();
        }
    }
}

