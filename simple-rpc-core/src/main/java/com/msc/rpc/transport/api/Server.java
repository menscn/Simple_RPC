package com.msc.rpc.transport.api;

import com.msc.rpc.common.domain.RPCRequest;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.transport.api
 * @Description: 服务端传输接口
 */
public interface Server {
    void run();

    /**
     * @return void
     * @Author mSc
     * @Description 关闭
     * @Param []
     **/
    void close() throws InterruptedException;
    
    /**
     * @return void
     * @Author mSc
     * @Description RPC请求处理函数
     * @Param [request, ctx] ctx用于标记handler,以便任务在线程池中处理完之后能从正确的channel中被刷出
     **/
    void handlerRPCRequest(RPCRequest request, ChannelHandlerContext ctx);
}

