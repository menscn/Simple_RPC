package com.msc.rpc.common.enumeration.support;

import com.msc.rpc.common.enumeration.ExceptionEnum;
import com.msc.rpc.common.exception.RPCException;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.enumeration.support
 * @Description: 扩展基础类型
 */
public interface ExtensionBaseType<T> {
    T getInstance();

    static ExtensionBaseType valueOf(Class enumType, String s) {
        Enum wantedEnum = Enum.valueOf(enumType, s);
        if (wantedEnum instanceof ExtensionBaseType) {
            return (ExtensionBaseType) wantedEnum;
        } else {
            throw new RPCException(ExceptionEnum.VALUE_OF_MUST_BE_APPLIED_TO_EXTENSION_ENUM_TYPE, "VALUE_OF_MUST_BE_APPLIED_TO_EXTENSION_ENUM_TYPE");
        }
    }
}
