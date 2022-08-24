package com.msc.rpc.serialize.protostuff;

import com.msc.rpc.common.enumeration.ExceptionEnum;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.serialize.api.Serializer;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.objenesis.ObjenesisStd;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.serialize.protostuff
 * @Description: protostuff序列化
 */
public class ProtostuffSerializer implements Serializer {
    //该对象作用:实例化 序列化对象
    private ObjenesisStd objenesis = new ObjenesisStd();

    @Override
    public <T> byte[] serialize(T o) throws RPCException {
        Class clz = o.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema schema = RuntimeSchema.createFrom(clz);
            return ProtostuffIOUtil.toByteArray(o, schema, buffer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RPCException(ExceptionEnum.SERIALIZE_ERROR, "SERIALIZE_ERROR");
        } finally {
            buffer.clear();
        }
    }

    @Override
    public <T> T deSerializer(byte[] bytes, Class<T> clazz) throws RPCException {
        T o = objenesis.newInstance(clazz); //实例化一个对象
        Schema<T> schema = RuntimeSchema.createFrom(clazz);
        ProtostuffIOUtil.mergeFrom(bytes, o, schema);
        return o;
    }
}
