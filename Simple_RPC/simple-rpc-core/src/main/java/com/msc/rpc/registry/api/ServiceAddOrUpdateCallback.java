package com.msc.rpc.registry.api;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.registry.api
 * @Description: 服务上线/更新回调接口 ClusterInvoker调用discovery()方法时，将此接口的实现类作为参数传入
 * -》当某个服务器上线/更新了服务的时候，注册中心回调该实现类的方法，添加/更新对应的serviceURL
 */
public interface ServiceAddOrUpdateCallback {
    void addOrUpdate(ServiceURL serviceURL);
}
