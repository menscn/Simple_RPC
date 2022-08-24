package com.mensc.rpc.client.call;

import com.msc.rpc.api.domain.User;
import com.msc.rpc.api.server.SimpleRPCService;

/**
 * @author mSc
 * @version 1.0
 * @Package com.mensc.rpc.client.call
 * @Description: 同步调用测试
 */
public class SyncCallService {
    private SimpleRPCService rpcService;    //远程服务的本地代理

    public void syncCallTest() {
        String result = rpcService.helloRPC(new User("pengqidalao"));
        log.info("同步调用结果:{}", result);
    }
}
