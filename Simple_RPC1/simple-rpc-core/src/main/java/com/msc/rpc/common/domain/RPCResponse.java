package com.msc.rpc.common.domain;

import io.netty.util.Recycler;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.domain
 * @Description: RPC响应
 */
@Data
public class RPCResponse implements Serializable {
    /**
     * 请求ID
     */
    private String requestID;

    /**
     * 调用失败原因
     */
    private Throwable errorCause;

    /**
     * 调用结果
     */
    private Object result;
    
    /**
     * 封装了RPCResponse的handle
     */
    private final transient Recycler.Handle<RPCResponse> handle;

    public RPCResponse(Recycler.Handle<RPCResponse> handle) {
        this.handle = handle;
    }

    /**
     * @return boolean
     * @Author mSc
     * @Description 判断是否有错误
     * @Param []
     **/
    public boolean hasError() {
        return errorCause != null;
    }

    /**
     * @return void
     * @Author mSc
     * @Description 回收对象至对象池
     * @Param []
     **/
    public void recyle() {
        requestID = null;
        errorCause = null;
        result = null;
        handle.recycle(this);
    }
}
