package com.msc.rpc.common.enumeration;

import com.msc.rpc.config.ReferenceConfig;
import com.msc.rpc.invocation.api.Invocation;
import com.msc.rpc.invocation.async.AsyncInvocation;
import com.msc.rpc.invocation.callback.CallbackInvocation;
import com.msc.rpc.invocation.oneway.OneWayInvocation;
import com.msc.rpc.invocation.sync.SyncInvocaton;
import com.msc.rpc.protocol.api.InvokeParam;
import com.msc.rpc.protocol.api.support.RPCInvokeParam;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.enumeration
 * @Description: 调用类型
 * 传入引用配置类信息，获取调用类型
 */
public enum InvocationType {
    SYNC(new SyncInvocaton()),            //同步调用
    ASYNC(new AsyncInvocation()),         //异步调用
    CALLBACK(new CallbackInvocation()),   //回调方式
    ONEWAY(new OneWayInvocation());       //oneWay调用

    private Invocation invocation;

    InvocationType(Invocation invocation) {
        this.invocation = invocation;
    }

    /**
     * @return com.msc.rpc.invocation.api.Invocation
     * @Author mSc
     * @Description 通过引用配置信息获取调用方式
     * @Param [invokeParam]
     **/
    public static Invocation get(InvokeParam invokeParam) {
        ReferenceConfig referenceConfig = ((RPCInvokeParam) invokeParam).getReferenceConfig();
        if (referenceConfig.isAsync()) {
            return ASYNC.invocation;
        } else if (referenceConfig.isCallback()) {
            return CALLBACK.invocation;
        } else if (referenceConfig.isOneWay()) {
            return ONEWAY.invocation;
        } else {
            return SYNC.invocation;
        }
    }
}