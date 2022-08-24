package com.msc.rpc.transport.wind.client;

import com.msc.rpc.common.domain.Message;
import com.msc.rpc.common.enumeration.ExceptionEnum;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.transport.api.Client;
import com.msc.rpc.transport.wind.constance.WindConstant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.transport.wind.client
 * @Description: wind客户端处理器
 */
@Slf4j
public class WindClientHandler extends SimpleChannelInboundHandler<Message> {
    private Client client;

    //空闲超时计数器,客户端每超过一次时间未读到服务端数据就+1, 收到后归零
    private AtomicInteger timeoutCount = new AtomicInteger(0);

    WindClientHandler(Client client) {
        this.client = client;
    }

    /**
     * @return void
     * @Author mSc
     * @Description 客户端接受到信息分发
     * @Param [ctx, msg]
     **/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        //收到信息,先将计数器归零
        timeoutCount.set(0);
        byte type = msg.getType();
        if (type == Message.PONG) {
            log.info("收到了服务器{}的心跳回应PONG！", client.getServiceURL().getServiceAddress());
            ctx.writeAndFlush(Message.PING_MSG);
        } else if (type == Message.REQUEST) {
            client.handlerCallbackRequest(msg.getRequest(), ctx);
        } else if (type == Message.RESPONSE) {
            client.handlerRPCResponse(msg.getResponse());
        }
    }

    /**
     * @return void
     * @Author mSc
     * @Description 空闲事件处理器, 当客户端在规定时间内没有收到消息, 将触发此方法
     * 保持心跳等
     * @Param [ctx, evt]
     **/
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //先判断事件是否为空闲事件
        if (evt instanceof IdleStateEvent) {
            if (timeoutCount.getAndIncrement() >= WindConstant.HEART_BEAT_TIMEOUT_MAX_TIMES) {
                log.info("超过重连次数");
                client.handlerException(new RPCException(ExceptionEnum.HEART_BEAT_TIMES_EXCEED, "HEART_BEAT_TIMES_EXCEED"));
            } else {
                log.info("指定时间内未收到服务端信息,发送心跳信号至:{}", client.getServiceURL().getServiceAddress());
                ctx.writeAndFlush(Message.PING_MSG);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * @return void
     * @Author mSc
     * @Description 异常捕获
     * @Param [ctx, cause]
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("客户端出现异常");
        cause.printStackTrace();
        log.info("正在断开与服务器{}的连接...", client.getServiceURL().getServiceAddress());
        //出现异常,直接断开连接
        client.handlerException(cause);
    }

    /**
     * @return void
     * @Author mSc
     * @Description 激活通道
     * @Param [ctx]
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端与服务器{}之间的通道已开启", client.getServiceURL().getServiceAddress());
    }
}
