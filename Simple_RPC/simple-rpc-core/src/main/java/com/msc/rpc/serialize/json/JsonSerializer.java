package com.msc.rpc.serialize.json;

import com.alibaba.fastjson.JSONObject;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.serialize.api.Serializer;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.serialize.json
 * @Description: 使用alibaba的Json包, 实现基于文档的序列化:可读性好,效率较低
 */
public class JsonSerializer implements Serializer {
    @Override
    public <T> byte[] serialize(T o) throws RPCException {
        return JSONObject.toJSONBytes(o);
    }

    @Override
    public <T> T deSerializer(byte[] bytes, Class<T> clazz) throws RPCException {
        return JSONObject.parseObject(bytes, clazz);
    }
}
