package com.msc.rpc.api.server;

import com.msc.rpc.api.callback.CallbackInterface;
import com.msc.rpc.api.domian.User;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.api.server
 * @Description: 带回调参数的服务接口
 */
public interface RPCServiceWithCallback {
    void hello(User user, CallbackInterface callbackInterface);
}
