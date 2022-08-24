package com.msc.rpc.serialize.jdk;

import com.msc.rpc.common.enumeration.ExceptionEnum;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.serialize.api.Serializer;

import java.io.*;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.serialize.jdk
 * @Description: 基于jdk自带的序列化与反序列化实现
 */
public class JDKSerializer implements Serializer {
    @Override
    public <T> byte[] serialize(T o) throws RPCException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            byte[] bytes = baos.toByteArray();
            baos.close();
            oos.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RPCException(ExceptionEnum.SERIALIZE_ERROR, "SERIALIZE_ERROR");
        }
    }

    @Override
    public <T> T deSerializer(byte[] bytes, Class<T> clazz) throws RPCException {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object res = ois.readObject();
            return clazz.cast(res); //强制类型转换
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RPCException(ExceptionEnum.DESERIALIZE_ERROR, "DESERIALIZE_ERROR");
        }
    }
}
