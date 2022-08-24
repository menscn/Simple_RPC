package com.msc.rpc.autoconfig.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.autoconfig.annotation
 * @Description: 远程服务的注解类
 * 添加了@RPCService注解的类将被暴露到注册中心
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RPCService {
    //实现的接口
    Class<?> interfaceClass() default void.class;

    boolean callback() default false;

    String callbackMethod() default "";

    int callbackParamIndex() default 1;
}