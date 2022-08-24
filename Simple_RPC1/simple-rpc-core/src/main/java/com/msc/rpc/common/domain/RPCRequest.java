package com.msc.rpc.common.domain;

import com.msc.rpc.common.util.TypeUtil;
import io.netty.util.Recycler;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.domain
 * @Description: RPC调用请求对象
 */
@Data
public class RPCRequest implements Serializable {
    private String requestID;

    private String interfaceName;

    private String methodName;

    private String[] parameterTypes;

    private Object[] parameters;

    /**
     * handle为Recyler对象池中封装RPCRequest的对象,不可序列化
     * 任何使用Recyler对象池复用技术的对象都会被封装成DefaultHandle对象(实现了Handle接口)
     * Handle接口只有一个方法:recycle(),用于回收对象至对象池
     */
    private final transient Recycler.Handle<RPCRequest> handle;

    public RPCRequest(Recycler.Handle<RPCRequest> handle) {
        this.handle = handle;
    }

    /**
     * @return java.lang.Class[]
     * @Author mSc
     * @Description 获取参数类型
     * @Param []
     **/
    public Class[] getParameterTypes() {
        Class[] parameterTypeClasses = new Class[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            String type = parameterTypes[i];
            try {
                if (TypeUtil.isPrimitive(type)) {
                    //基本类型
                    parameterTypeClasses[i] = TypeUtil.map(type);
                } else {
                    //非基本类型
                    parameterTypeClasses[i] = Class.forName(type);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return parameterTypeClasses;
    }

    /**
     * @return void
     * @Author mSc
     * @Description 传入String类型Type，传给parameterTypes
     * @Param [parameterTypes]
     **/
    public void setParameterTypes(String[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    /**
     * @return void
     * @Author mSc
     * @Description 将class转换为String的参数类型
     * @Param [parametersTypes]
     **/
    public void setParameterTypes(Class[] parametersTypes) {
        String[] stringParametersTypes = new String[parametersTypes.length];
        for (int i = 0; i < parametersTypes.length; i++) {
            stringParametersTypes[i] = parametersTypes[i].getName();
        }
        this.parameterTypes = stringParametersTypes;
    }

    /**
     * @return java.lang.String
     * @Author mSc
     * @Description 拼接请求信息
     * @Param []
     **/
    public String key() {
        return interfaceName + "." +
                methodName + "." +
                Arrays.toString(parameterTypes) + "." +
                Arrays.toString(parameters);
    }

    /**
     * @return void
     * @Author mSc
     * @Description 回收对象至对象池
     * @Param []
     **/
    public void recyle() {
        requestID = null;
        interfaceName = null;
        methodName = null;
        parameterTypes = null;
        parameters = null;
        handle.recycle(this);
    }
}
