package com.msc.rpc.common.exception;

import com.msc.rpc.common.enumeration.ExceptionEnum;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.exception
 * @Description: RPC异常
 */
public class RPCException extends RuntimeException {
    /**
     * 异常枚举类实例
     */
    private ExceptionEnum exceptionEnum;

    /**
     * @return
     * @Author mSc
     * @Description 抛出异常的信息
     * @Param [exceptionEnum, message]
     **/
    public RPCException(ExceptionEnum exceptionEnum, String message) {
        super(message);
        this.exceptionEnum = exceptionEnum;
    }

    /**
     * @return
     * @Author mSc
     * @Description 重载上一个方法
     * @Param [throwable, exceptionEnum, message]
     **/
    public RPCException(Throwable throwable, ExceptionEnum exceptionEnum, String message) {
        super(message, throwable);
        this.exceptionEnum = exceptionEnum;
    }
}
