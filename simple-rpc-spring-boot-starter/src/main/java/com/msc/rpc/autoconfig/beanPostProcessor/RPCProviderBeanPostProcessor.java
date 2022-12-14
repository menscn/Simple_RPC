package com.msc.rpc.autoconfig.beanPostProcessor;

import com.msc.rpc.autoconfig.annotation.RPCService;
import com.msc.rpc.common.enumeration.ExceptionEnum;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.config.ServiceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.autoconfig.beanPostProcessor
 * @Description: rpc服务端后置处理器, 用于创建ServiceConfig实例
 */
public class RPCProviderBeanPostProcessor {
    @Slf4j
    public class RPCProviderBeanPostProcessor extends AbstractBeanPostProcessor {

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            Class<?> beanClass = bean.getClass();
            if (!beanClass.isAnnotationPresent(RPCService.class)) {
                return bean;
            }

            RPCService service = beanClass.getAnnotation(RPCService.class);
            Class<?> interfaceClass = service.interfaceClass();
            if (interfaceClass == void.class) {
                Class<?>[] interfaces = beanClass.getInterfaces();
                if (interfaces.length >= 1) {
                    interfaceClass = interfaces[0];
                } else {
                    throw new RPCException(ExceptionEnum.SERVICE_DID_NOT_IMPLEMENT_ANY_INTERFACE, beanName + "未实现任何服务接口");
                }
            }
            ServiceConfig serviceConfig = ServiceConfig.builder().
                    interfaceName(interfaceClass.getName()).
                    interfaceClass((Class<Object>) interfaceClass).
                    isCallback(service.callback()).
                    callbackMethod(service.callbackMethod()).
                    callbackParameterIndex(service.callbackParamIndex()).
                    ref(bean).
                    build();
            initConfig(serviceConfig);
            serviceConfig.expot();
            log.info("暴露服务:{}", interfaceClass);
            return bean;
        }
    }
}
