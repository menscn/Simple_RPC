package com.msc.rpc.registry.api;

import java.util.List;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.registry.api
 * @Description: 服务下线回调接口
 * ClusterInvoker调用discovery()接口的时候，将此接口的实现类作为参数传入
 * 服务器有服务下线的时候，注册中心回调该接口的实现方法，删除对应的ServiceURL
 */
public interface ServiceOfflineCallback {
    /**
     * @return void
     * @Author mSc
     * @Description 移除不存在的服务
     * @Param [newServiceURLs]
     **/
    void removeNotExisted(List<ServiceURL> newServiceURLs);
}
