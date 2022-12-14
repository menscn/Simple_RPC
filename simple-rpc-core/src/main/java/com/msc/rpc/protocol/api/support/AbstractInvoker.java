package com.msc.rpc.protocol.api.support;

import com.msc.rpc.client.filter.Filter;
import com.msc.rpc.common.domain.RPCRequest;
import com.msc.rpc.common.domain.RPCResponse;
import com.msc.rpc.common.enumeration.ExceptionEnum;
import com.msc.rpc.common.enumeration.InvocationType;
import com.msc.rpc.common.exception.RPCException;
import com.msc.rpc.config.GlobalConfig;
import com.msc.rpc.protocol.api.InvokeParam;
import com.msc.rpc.protocol.api.Invoker;
import com.msc.rpc.registry.api.ServiceURL;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.protocol.api.support
 * @Description: 抽象调用者，引入filter
 */
@Slf4j
public abstract class AbstractInvoker<T> implements Invoker {
    private String interfaceName;

    private Class<T> interfaceClass;

    private GlobalConfig globalConfig;

    @Override
    public String getInterfaceName() {
        return interfaceName;
    }

    @Override
    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    @Override
    public ServiceURL getServiceURL() {
        return ServiceURL.DEFAULT_SERVICE_URL;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    @Override
    public RPCResponse invoke(InvokeParam invokeParam) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Function<RPCRequest, Future<RPCResponse>> logic = getProcess();
        if (logic == null) {
            throw new RPCException(ExceptionEnum.GET_PROCESSOR_METHOD_MUST_BE_OVERRIDE, "GET_PROCESSOR_METHOD_MUST_BE_OVERRIDE");
        }
        return InvocationType.get(invokeParam).invoke(invokeParam, logic);
    }

    private Function<RPCRequest, Future<RPCResponse>> getProcess() {
        return null;
    }

    @SuppressWarnings("unchecked")
    public Invoker<T> buildFilterChain(List<Filter> filters) {
        return new DelegateInvoker<T>(this) {
            private ThreadLocal<AtomicInteger> filterIndex = new ThreadLocal<AtomicInteger>() {
                @Override
                protected AtomicInteger initialValue() {
                    return new AtomicInteger(0);
                }
            };

            @Override
            public RPCResponse invoke(InvokeParam invokeParam) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
                //打印当前线程的filter索引位置
                log.info("filterIndex:" + filterIndex.get().get() + ",InvokeParam:" + invokeParam);
                final Invoker<T> invokerWrappedFilters = this;
                //若filter链条仍未扫描完毕，继续扫描
                if (filterIndex.get().get() < filters.size()) {
                    //
                    return filters.get(filterIndex.get().getAndIncrement()).invoke(new AbstractInvoker() {
                        @Override
                        public String getInterfaceName() {
                            return getDelegate().getInterfaceName();
                        }

                        @Override
                        public Class<T> getInterfaceClass() {
                            return getDelegate().getInterfaceClass();
                        }

                        @Override
                        public ServiceURL getServiceURL() {
                            return getDelegate().getServiceURL();
                        }

                        @Override
                        public RPCResponse invoke(InvokeParam invokeParam) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
                            return invokerWrappedFilters.invoke(invokeParam);
                        }
                    }, invokeParam);
                }
                filterIndex.get().set(0);
                return getDelegate().invoke(invokeParam);
            }
        };
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}