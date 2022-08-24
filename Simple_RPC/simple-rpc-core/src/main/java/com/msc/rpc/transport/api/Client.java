package com.msc.rpc.transport.api;

import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.registry.api.ServiceURL;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.Future;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.transport.api
 * @Description: 客户端传输接口
 */
public interface Client {
    Future<RPCResponse> submit(RPCRequest request);

    void close();

    /**
     * @return void
     * @Author mSc
     * @Description 异常逻辑处理器
     * @Param [throwable]
     **/
    void handlerException(Throwable throwable);

    /**
     * @return void
     * @Author mSc
     * @Description 调用结果处理器
     * @Param [response]
     **/
    void handlerRPCResponse(RPCResponse response);

    /**
     * @return void
     * @Author mSc
     * @Description 回调请求逻辑处理器
     * @Param [request, ctx]
     **/
    void handlerCallbackRequest(RPCRequest request, ChannelHandlerContext ctx);

    /**
     * @return void
     * @Author mSc
     * @Description 更新服务配置
     * @Param [serviceURL]
     **/
    void updateServiceConfig(ServiceURL serviceURL);

    /**
     * @return com.msc.rpc.registry.api.ServiceURL
     * @Author mSc
     * @Description 获取与该客户端实例相连接的服务信息
     * @Param []
     **/
    ServiceURL getServiceURL();

    boolean isAvailable();
}
