package com.msc.rpc.transport.wind.client;

import com.msc.rpc.transport.api.support.netty.AbstractNettyClient;
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
 * @Package com.msc.rpc.transport.wind.client
 * @Description: WIND =客户端
 */
public class WindClient extends AbstractNettyClient {
    /**
     * @return io.netty.channel.ChannelInitializer
     * @Author mSc
     * @Description 初始化客户端生产线
     * @Param []
     **/
    @Override
    protected ChannelInitializer initPipeline() {
        return new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline()
                        //空闲检测逻辑处理器
                        .addLast("IdleStateHandler", new IdleStateHandler(0, WindConstant.IDLE_INTERVAL, 0))
                        //Bytebuf -> Message
                        .addLast("LengthFieldPrepender", new LengthFieldPrepender(CommunicationConstant.LENGTH_FIELD_LENGTH, CommunicationConstant.LENGTH_ADJUSTMENT))
                        //Message -> Bytebuf
                        .addLast("WindEncoder", new WindEncoder(getGlobalConfig().getSerializer()))
                        //Bytrbuf -> Message
                        .addLast("", new LengthFieldBasedFrameDecoder(CommunicationConstant.MAX_FRAME_LENGTH,
                                CommunicationConstant.LENGTH_FIELD_OFFSET,
                                CommunicationConstant.LENGTH_FIELD_LENGTH,
                                CommunicationConstant.LENGTH_ADJUSTMENT,
                                CommunicationConstant.INITIAL_BYTES_TO_STRIP))
                        //Message -> Message
                        .addLast("", new WindDecoder(getGlobalConfig().getSerializer()))
                        .addLast("", new WindClientHandler(WindClient.this));
            }
        };
    }
}