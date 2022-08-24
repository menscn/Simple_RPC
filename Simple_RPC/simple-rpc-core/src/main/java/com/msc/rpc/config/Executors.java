package com.msc.rpc.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.config
 * @Description: 客户端与服务端通用的executor配置类，配置以下属性
 * 1）隶属于provider的ExecutorConfig--server
 * 2）隶属于consumer的ExecutorConfig--client
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Executors {
    private ExecutorConfig server;

    private ExecutorConfig client;

    /**
     * @return void
     * @Author mSc
     * @Description 关闭客户端服务端
     * @Param []
     **/
    public void close() {
        if (server != null) {
            server.close();
        }
        if (client != null) {
            client.close();
        }
    }
}
