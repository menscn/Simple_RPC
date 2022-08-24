package com.msc.rpc.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.domain
 * @Description: 消息对象, 封装了RPC请求对象、RPC响应对象以及消息类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private RPCRequest request;

    private RPCResponse response;

    private byte type;

    public Message(byte type) {
        this.type = type;
    }

    /**
     * @return com.msc.rpc.common.domain.Message
     * @Author mSc
     * @Description 封装请求信息
     * @Param [request]
     **/
    public static Message buildRequest(RPCRequest request) {
        return new Message(request, null, REQUEST);
    }

    /**
     * @return com.msc.rpc.common.domain.Message
     * @Author mSc
     * @Description 封装响应信息
     * @Param [response]
     **/
    public static Message buildResponse(RPCResponse response) {
        return new Message(null, response, RESPONSE);
    }

    //所有消息类型
    public static final byte PING = 1;          //心跳检测PING类型
    public static final byte PONG = 1 << 1;     //心跳检测PONG类型
    public static final byte REQUEST = 1 << 2;  //请求消息类型
    public static final byte RESPONSE = 1 << 3; //响应消息类型
    public static final Message PING_MSG = new Message(PING);  //PING消息对象
    public static final Message PONG_MSG = new Message(PONG);  //PONG消息对象
    
}
