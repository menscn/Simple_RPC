package com.msc.rpc.config;

import com.msc.rpc.protocol.api.Protocol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.config
 * @Description: 协议配置类，主要配置以下信息
 * 1）协议类型：TCP、HTTP、InJvm
 * 2）端口号
 * 3）Protocol实例
 * 4）Executors实例
 * 5）默认端口号：8000
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ProtocolConfig {
    //默认端口号
    public static final Integer DEFAULT_PORT = Integer.valueOf(8000);
    private String type;
    private Integer port;
    //协议实例
    private Protocol protocolInstance;
    //执行者实例
    private Executors executors;

    /**
     * @return int
     * @Author mSc
     * @Description 获取当前端口
     * @Param [][]
     **/
    public int getPort() {
        if (port != null) {
            return port;
        }
        return DEFAULT_PORT;
    }

    /**
     * @return void
     * @Author mSc
     * @Description 关闭两个实例对象
     * @Param []
     **/
    public void close() throws InterruptedException {
        protocolInstance.close();
        executors.close();
    }
}
