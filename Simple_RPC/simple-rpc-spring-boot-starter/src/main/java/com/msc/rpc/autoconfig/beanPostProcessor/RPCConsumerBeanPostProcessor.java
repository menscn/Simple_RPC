package com.msc.rpc.autoconfig.beanPostProcessor;

import com.msc.rpc.autoconfig.annotation.RPCReference;
import com.msc.rpc.client.filter.Filter;
import com.msc.rpc.common.ExtensionLoader;
import com.msc.rpc.config.ReferenceConfig;
import org.springframework.beans.BeansException;

import java.lang.reflect.Field;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.autoconfig.beanPostProcessor
 * @Description:
 */
public class RPCConsumerBeanPostProcessor extends AbstractBeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        //扫描bean的所有字段
        Field[] fields = beanClass.getFields();
        for (Field field : fields) {
            field.setAccessible(true);  //将所有字段设为可访问的
            //获取字段类型(对引用服务来说就是接口类型)
            Class<?> interfaceClass = field.getType();
            //获取字段的@RPCReference注解
            RPCReference reference = field.getAnnotation(RPCReference.class);
            if (reference != null) {
                //字段存在@RPCReference注解,使用注解配置ReferenceConfig对象
                ReferenceConfig referenceConfig = ReferenceConfig.createReferenceConfig(
                        interfaceClass.getName(),
                        interfaceClass,
                        reference.async(),
                        reference.oneWay(),
                        reference.callback(),
                        reference.callbackMethod(),
                        reference.callbackParamIndex(),
                        reference.timeout(),
                        false,
                        ExtensionLoader.getInstance().load(Filter.class)
                );
                initConfig(referenceConfig);
                try {
                    field.set(bean, referenceConfig.get());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}