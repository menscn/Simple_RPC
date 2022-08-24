package com.mensc.rpc.client.call;

import com.msc.rpc.api.domain.User;
import com.msc.rpc.api.server.SimpleRPCService;
import com.msc.rpc.autoconfig.annotation.RPCReference;
import com.msc.rpc.common.context.RPCThreadLocalContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @author mSc
 * @version 1.0
 * @Package com.mensc.rpc.client.call
 * @Description: 异步调用测试
 */
@Component
@Slf4j
public class AsyncCallService {
    @RPCReference(async = true)
    private SimpleRPCService service;

    public void asyncCall() throws Exception {
        User user = new User("msc");
        service.helloRPC(user);
        Future<String> future = RPCThreadLocalContext.getContext().getFuture();
        log.info("异步调用结果:{}", future.get());
    }
}
