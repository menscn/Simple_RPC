package com.msc.rpc.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.util
 * @Description: 类型工具类
 */
public class TypeUtil {
    private static Map<String, Class> CLASS_MAP = new HashMap<>();

    static {
        CLASS_MAP.put("int", int.class);
        CLASS_MAP.put("float", float.class);
        CLASS_MAP.put("double", double.class);
        CLASS_MAP.put("boolean", boolean.class);
        CLASS_MAP.put("byte", byte.class);
        CLASS_MAP.put("char", char.class);
        CLASS_MAP.put("short", short.class);
        CLASS_MAP.put("long", long.class);
    }

    /**
     * @return boolean
     * @Author mSc
     * @Description 判断是否为基本数据类型
     * @Param [type]
     **/
    public static boolean isPrimitive(String type) {
        return CLASS_MAP.containsKey(type);
    }

    /**
     * @return java.lang.Class
     * @Author mSc
     * @Description 获取map中的类型的value
     * @Param [type]
     **/
    public static Class map(String type) {
        return CLASS_MAP.get(type);
    }
}
