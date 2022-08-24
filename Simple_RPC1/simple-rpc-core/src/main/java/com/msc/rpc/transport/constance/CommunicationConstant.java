package com.msc.rpc.transport.constance;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.transport.constance
 * @Description: 客户端与服务端之间的通讯常数
 * 可以处理半包黏包问题
 */
public class CommunicationConstant {
    public static final int MAX_FRAME_LENGTH = 1024 * 1024;   //单个包的最大长度为1K

    public static final int LENGTH_FIELD_LENGTH = 4;        //长度字段字节数

    public static final int LENGTH_FIELD_OFFSET = 0;        //长度域偏移量

    public static final int LENGTH_ADJUSTMENT = 0;          //数据字段相对于长度域的偏移量

    public static final int INITIAL_BYTES_TO_STRIP = 4;     //从整个包的第一个字节开始,向后忽略4个字节
}

