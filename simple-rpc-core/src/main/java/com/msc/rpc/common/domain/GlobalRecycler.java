package com.msc.rpc.common.domain;

import com.msc.rpc.common.enumeration.ExceptionEnum;
import com.msc.rpc.common.exception.RPCException;
import io.netty.util.Recycler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.domain
 * @Description: 基于netty的全局对象池 -》 实现对象的复用-》 避免频繁创建对象以及GC
 */
public class GlobalRecycler {
    /**
     * 存储对象类型及其对象池
     * k: 循环对象类型
     * v: 循环对象
     */
    private static Map<Class<?>, Recycler<?>> RECYCLER = new HashMap<>();

    /**
     * @return boolean
     * @Author mSc
     * @Description 判断是否能够复用
     * @Param [clazz]
     **/
    public static boolean isReusable(Class<?> clazz) {
        return RECYCLER.containsKey(clazz);
    }

    /**
     * @return T
     * @Author mSc
     * @Description 复用对象，若不可复用，则抛出异常
     * @Param [clazz]
     **/
    public static <T> T reuse(Class<T> clazz) {
        if (!isReusable(clazz)) {
            throw new RPCException(ExceptionEnum.RECYCLER_ERROR, "RECYCLER_ERROR");
        }
        return (T) RECYCLER.get(clazz).get();
    }
}
