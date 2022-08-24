package com.msc.rpc.executor.api;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.executor.api
 * @Description: 任务执行器接口:
 * 1)关闭线程池
 * 2)向线程池提交任务
 * 3)初始化线程池(参数为线程池内线程数量)
 */
public interface TaskExecutor {
    void close();

    void submit(Runnable task);

    void init(int threads);
}

