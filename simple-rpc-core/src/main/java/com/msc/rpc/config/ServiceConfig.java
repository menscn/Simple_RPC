package com.msc.rpc.config;

import com.msc.rpc.config.supoport.AbstractConfig;
import com.msc.rpc.protocol.api.Exporter;
import com.msc.rpc.protocol.api.Invoker;
import lombok.Builder;
import lombok.Data;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.config
 * @Description: 服务配置信息
 */
@Data
@Builder
public class ServiceConfig<T> extends AbstractConfig {
    private String interfaceName;
    private Class<T> interfaceClass;

    //表示此接口是有一个callback参数用来回调，而不是这个接口为一个回调接口
    private boolean isCallback;

    private String callbackMethod;
    private int callbackParameterIndex = 1;
    private boolean isCallbackInterface;
    private T ref;
    private Exporter<T> exporter;

    /**
     * @return void
     * @Author mSc
     * @Description 暴露服务，供调用
     * @Param []
     **/
    public void expot() {
        Invoker<T> invoker = getApplicationConfig().getProxyFactoryInstance().getInvoker(ref, interfaceClass);
        exporter = getProtocolConfig().getProtocolInstance().export(invoker, this);
    }
}
