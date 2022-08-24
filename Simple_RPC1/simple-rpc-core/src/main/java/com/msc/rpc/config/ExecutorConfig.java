package com.msc.rpc.config;


import com.msc.rpc.executor.api.TaskExecutor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.config
 * @Description: 执行者配置类，主要配置以下属性
 * 1）线程池线程数量
 * 2）executor类型，有两种：disruptor高性能队列、线程池（Executor框架）
 * 3）TaskExecutor实例
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutorConfig {
    /**
     * 默认线程数为处理器核心数量
     */
    public static final Integer DEFAULT_THREADS = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池线程数量
     */
    private Integer threads;
    /**
     * executor类型
     */
    private String type;
    /**
     * TaskExecutor实例
     */
    private TaskExecutor taskExecutorInstance;

    /**
     * @return int
     * @Author mSc
     * @Description 获取线程池线程数量
     * @Param []
     **/
    public int getThreads() {
        if (threads != null) {
            return threads;
        }
        return DEFAULT_THREADS;
    }

    /**
     * @return void
     * @Author mSc
     * @Description 关闭TaskExecutor实例
     * @Param []
     **/
    public void close() {
        if (taskExecutorInstance != null) {
            taskExecutorInstance.close();
        }
    }
}
