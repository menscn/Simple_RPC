package com.msc.rpc.api.server;

import com.msc.rpc.api.domian.User;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.api.server
 * @Description: 简单RPC服务接口
 */
public interface SimpleRPCService {
    public String helloRPC(User user);
}
