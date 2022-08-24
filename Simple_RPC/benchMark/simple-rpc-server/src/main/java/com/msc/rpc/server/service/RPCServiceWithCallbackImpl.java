package com.msc.rpc.server.service;

import com.msc.rpc.api.callback.CallbackInterface;
import com.msc.rpc.api.domain.User;
import com.msc.rpc.api.server.RPCServiceWithCallback;
import com.msc.rpc.autoconfig.annotation.RPCService;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.server.service
 * @Description: 带回调参数的服务接口实现类
 */
@RPCService(interfaceClass = RPCServiceWithCallback.class, callback = true, callbackMethod = "getInfoFromClient")
public class RPCServiceWithCallbackImpl implements RPCServiceWithCallback {
    @Override
    public void hello(User user, CallbackInterface callbackInterface) {
        String result = "i am RPCServer,i'm calling " + user.getUserName() + " back...";
        callbackInterface.getInfoFromClient(result);
    }
}