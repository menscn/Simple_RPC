package com.msc.rpc.serialize.hessian;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.msc.rpc.common.enumeration.ExceptionEnum;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.serialize.api.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.serialize.hessian
 * @Description: Hessian协议序列化/反序列化算法
 */
public class HessianSerializer implements Serializer {
    @Override
    public <T> byte[] serialize(T o) throws RPCException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HessianOutput output = new HessianOutput(baos);
            output.writeObject(o);
            output.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RPCException(ExceptionEnum.SERIALIZE_ERROR, "SERIALIZE_ERROR");
        }
    }

    @Override
    public <T> T deSerializer(byte[] bytes, Class<T> clazz) throws RPCException {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            HessianInput input = new HessianInput(bais);
            return (T) input.readObject(clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RPCException(ExceptionEnum.DESERIALIZE_ERROR, "DESERIALIZE_ERROR");
        }
    }
}
