package com.msc.rpc.transport.server;

import com.msc.rpc.transport.api.support.netty.AbstractNettyServer;
import com.msc.rpc.transport.constance.CommunicationConstant;
import com.msc.rpc.transport.wind.codec.WindDecoder;
import com.msc.rpc.transport.wind.codec.WindEncoder;
import com.msc.rpc.transport.wind.constance.WindConstant;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.transport.server
 * @Description: Wind服务端，基于netty的具体实现
 */
public class WindServer extends AbstractNettyServer {
    /**
     * @return io.netty.channel.ChannelInitializer
     * @Author mSc
     * @Description 初始化生产线，添加处理器
     * @Param []
     **/
    @Override
    protected ChannelInitializer initPipeline() {
        return new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast("IdleStateHandler", new IdleStateHandler(WindConstant.IDLE_INTERVAL * WindConstant.HEART_BEAT_TIMEOUT_MAX_TIMES, 0, 0))
                        .addLast("LengthFieldPrepender", new LengthFieldPrepender(CommunicationConstant.LENGTH_FIELD_LENGTH, CommunicationConstant.LENGTH_ADJUSTMENT))
                        .addLast("WindEncoder", new WindEncoder(getGlobalConfig().getSerializer()))
                        .addLast("LengthFieldBasedFrameDecoder",
                                new LengthFieldBasedFrameDecoder(CommunicationConstant.MAX_FRAME_LENGTH,
                                        CommunicationConstant.LENGTH_FIELD_OFFSET,
                                        CommunicationConstant.LENGTH_FIELD_LENGTH,
                                        CommunicationConstant.LENGTH_ADJUSTMENT,
                                        CommunicationConstant.INITIAL_BYTES_TO_STRIP))
                        .addLast("WindDecoder", new WindDecoder(getGlobalConfig().getSerializer()))
                        .addLast("WindServerHandler", new WindServerHandler(WindServer.this));
            }
        };
    }
}
