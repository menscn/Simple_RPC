package com.msc.rpc.transport.server;

import com.msc.rpc.common.domain.Message;
import com.msc.rpc.transport.wind.constance.WindConstant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.transport.server
 * @Description: wind服务端处理器
 */
@Slf4j
public class WindServerHandler extends SimpleChannelInboundHandler<Message> {
    private WindServer windServer;

    //重试次数
    private AtomicInteger timeoutCount = new AtomicInteger(0);

    WindServerHandler(WindServer windServer) {
        this.windServer = windServer;
    }

    /**
     * @return void
     * @Author mSc
     * @Description 接受到客户端的信息及其处理
     * @Param [ctx, msg]
     **/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        //收到信息第一件事,将计数器置零
        timeoutCount.set(0);

        byte type = msg.getType();
        if (type == Message.PING) {
            log.info("服务端收到PING心跳");
            ctx.writeAndFlush(Message.PONG_MSG);
        } else {
            log.info("服务端收到RPC请求:{}", msg.getRequest());
            windServer.handlerRPCRequest(msg.getRequest(), ctx);
        }
    }

    /**
     * 当超过规定时间没有收到客户端请求,则关闭连接
     *
     * @param ctx
     * @param evt
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            if (timeoutCount.getAndIncrement() >= WindConstant.HEART_BEAT_TIMEOUT_MAX_TIMES) {
                ctx.close();
                log.info("超过丢失心跳的次数阀值,关闭连接");
            } else {
                log.info("超过规定时间未收到客户端的心跳或正常信息");
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        try {
            //出现异常,先对异常进行打印
            cause.printStackTrace();
        } finally {
            ctx.close();
        }
    }
}
