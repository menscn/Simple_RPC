package com.msc.rpc.transport.wind.codec;

import com.msc.rpc.common.domain.Message;
import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.serialize.api.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.transport.wind.codec
 * @Description: wind解码
 */
public class WindDecoder extends ByteToMessageDecoder {
    private Serializer serializer;

    public WindDecoder(Serializer serializer) {
        this.serializer = serializer;
    }

    /**
     * 单例模式,多个pipeline共享handler
     */
    //public static final WindEncoder INSTANCE = new WindEncoder();

    /**
     * @return void
     * @Author mSc
     * @Description 解码
     * @Param [ctx, in, out]
     **/
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte type = in.readByte();
        if (type == Message.PING) {
            out.add(Message.PING_MSG);
        } else if (type == Message.PONG) {
            out.add(Message.PONG_MSG);
        } else {
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            if (type == Message.REQUEST) {
                out.add(Message.buildRequest(serializer.deSerializer(bytes, RPCRequest.class)));
            } else if (type == Message.RESPONSE) {
                out.add(Message.buildResponse(serializer.deSerializer(bytes, RPCResponse.class)));
            }
        }
    }
}
