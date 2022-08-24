package com.msc.rpc.common.enumeration;

import com.msc.rpc.common.enumeration.support.ExtensionBaseType;
import com.msc.rpc.executor.api.TaskExecutor;
import com.msc.rpc.executor.threadPool.ThreadPoolTaskExecutor;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.enumeration
 * @Description: 执行器类型
 * 引入
 */
public enum ExecutorType implements ExtensionBaseType<TaskExecutor> {
    THREADPOOL(new ThreadPoolTaskExecutor()); //线程池

    private TaskExecutor executor;

    ExecutorType(TaskExecutor executor) {
        this.executor = executor;
    }

    @Override
    public TaskExecutor getInstance() {
        return executor;
    }
}
