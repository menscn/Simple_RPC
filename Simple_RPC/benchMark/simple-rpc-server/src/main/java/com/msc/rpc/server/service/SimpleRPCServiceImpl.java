package com.msc.rpc.server.service;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.server.service
 * @Description: 简单的服务接口实现类
 */
@RPCService(interfaceClass = SimpleRPCService.class)
public class SimpleRPCServiceImpl implements SimpleRPCService {
    @Override
    public String helloRPC(User user) {
        return "remote_call success!";
    }
}