package com.msc.rpc.common.enumeration;

import com.msc.rpc.common.enumeration.support.ExtensionBaseType;
import com.msc.rpc.serialize.api.Serializer;
import com.msc.rpc.serialize.hessian.HessianSerializer;
import com.msc.rpc.serialize.jdk.JDKSerializer;
import com.msc.rpc.serialize.json.JsonSerializer;
import com.msc.rpc.serialize.protostuff.ProtostuffSerializer;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.enumeration
 * @Description: 序列化类型选择
 * 可以有四种选择
 */
public enum SerializerType implements ExtensionBaseType<Serializer> {
    JDK(new JDKSerializer()),                     //jdk原生序列化
    HESSIAN(new HessianSerializer()),             //hessian序列化
    PROTOSTUFF(new ProtostuffSerializer()),       //protoStuff序列化
    JSON(new JsonSerializer());                   //Json序列化

    private Serializer serializer;

    SerializerType(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public Serializer getInstance() {
        return serializer;
    }
}
