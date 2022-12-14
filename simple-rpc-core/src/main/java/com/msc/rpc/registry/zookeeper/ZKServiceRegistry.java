package com.msc.rpc.registry.zookeeper;

import com.msc.rpc.common.enumeration.ExceptionEnum;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.config.RegistryConfig;
import com.msc.rpc.registry.api.ServiceAddOrUpdateCallback;
import com.msc.rpc.registry.api.ServiceOfflineCallback;
import com.msc.rpc.registry.api.ServiceURL;
import com.msc.rpc.registry.api.support.AbstractServiceRegistry;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.registry.zookeeper
 * @Description: zookeeper服务配置
 */
public class ZKServiceRegistry extends AbstractServiceRegistry {
    private ZKSupport zkSupport;

    private static final String ZK_REGISTRY_PATH = "/srpc";

    private static final long TIME_WAIT = 10000000L;

    private volatile static Thread discorveyThread;

    public ZKServiceRegistry(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }

    @Override
    public void init() {
        zkSupport = new ZKSupport();
        zkSupport.connect(registryConfig.getAddress());
    }

    @Override
    public void discover(String interfaceName, ServiceAddOrUpdateCallback serviceAddOrUpdateCallback, ServiceOfflineCallback serviceOfflineCallback) {
        DebugLogger log = null;
        log.info("discovering……");
        discorveyThread = Thread.currentThread();
        watchInterface(interfaceName, serviceAddOrUpdateCallback, serviceOfflineCallback);
        LockSupport.parkNanos(this, TIME_WAIT); //阻塞线程,直到服务发现完成为止,防止ClusterInvoker中的map还未初始化就被使用
    }

    /**
     * @return void
     * @Author mSc
     * @Description 监视接口
     * @Param [interfaceName, serviceAddOrUpdateCallback, serviceOfflineCallback]
     **/
    private void watchInterface(String interfaceName, ServiceAddOrUpdateCallback serviceAddOrUpdateCallback, ServiceOfflineCallback serviceOfflineCallback) {
        String path = generatePath(interfaceName);
        try {
            List<String> address = zkSupport.getChildren(path, watchEvent -> {
                if (watchEvent.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
                    watchInterface(interfaceName, serviceAddOrUpdateCallback, serviceOfflineCallback);
                }
            });

            List<ServiceURL> addList = new ArrayList<>();

            for (String node : address) {
                addList.add(watchService(interfaceName, node, serviceAddOrUpdateCallback));
            }

            serviceOfflineCallback.removeNotExisted(addList);
            LockSupport.unpark(discorveyThread);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RPCException(ExceptionEnum.REGISTRY_ERROR, "ZK故障");
        }
    }

    /**
     * @return com.msc.rpc.registry.api.ServiceURL
     * @Author mSc
     * @Description 监视zookeeper服务
     * @Param [interfaceName, node, serviceAddOrUpdateCallback]
     **/
    private ServiceURL watchService(String interfaceName, String node, ServiceAddOrUpdateCallback serviceAddOrUpdateCallback) {
        String path = generatePath(interfaceName);
        try {
            byte[] bytes = zkSupport.getData(path + "/" + node, WatchEvent -> {
                if (WatchEvent.getType() == Watcher.Event.EventType.NodeDataChanged) {
                    watchService(interfaceName, node, serviceAddOrUpdateCallback);
                }
            });

            ServiceURL serviceURL = ServiceURL.parse(new String(bytes, Charset.forName("UTF-8")));
            serviceAddOrUpdateCallback.addOrUpdate(serviceURL);
            return serviceURL;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RPCException(ExceptionEnum.REGISTRY_ERROR, "ZK故障");
        }
    }

    @Override
    public void register(String serviceAddress, String interfaceName, Class<?> interfaceClass) {
        String path = generatePath(interfaceName);
        try {
            zkSupport.createPathIfAbsent(path, CreateMode.PERSISTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        zkSupport.createNodeIfAbsent(serviceAddress, path);
    }

    @Override
    public void close() throws InterruptedException {
        if (zkSupport != null) {
            zkSupport.close();
        }
    }

    /**
     * 路径
     *
     * @param interfaceName
     * @return String
     */
    private String generatePath(String interfaceName) {
        return ZK_REGISTRY_PATH + "/" + interfaceName;
    }
}
