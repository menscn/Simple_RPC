package com.msc.rpc.api.callback;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.api.callback
 * @Description: 回调接口
 */
public interface CallbackInterface {
    /**
     * @return void
     * @Author mSc
     * @Description 回调方法
     * @Param [result] -》 rpc返回请求结果
     **/
    void getInfoFromClient(String result);
}
