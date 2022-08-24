package com.msc.rpc.common.context;

import lombok.extern.log4j.Log4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.context
 * @Description: RPC活跃度
 */
@Log4j
public class RPCStatus {
    /**
     * RPC请求活跃度映射表 线程安全 k:由服务接口名+方法名+服务器IP地址生产的key v:活跃度
     */
    private static final Map<String, Integer> RPC_ACTIVE_COUNT = new ConcurrentHashMap<>();

    /**
     * @return int
     * @Author mSc
     * @Description 获取活跃度
     * @Param [interfaceName, methodName, address]
     **/
    public static synchronized int getActivity(
            String interfaceName, String methodName, String address) {
        Integer count = RPC_ACTIVE_COUNT.get(generateKey(interfaceName, methodName, address));
        return count == null ? 0 : count;
    }

    /**
     * @return void
     * @Author mSc
     * @Description 活跃度+1 或 default
     * @Param [interfaceName, methodName, address]
     **/
    public synchronized static void inActivity(String interfaceName, String methodName, String address) {
        String key = generateKey(interfaceName, methodName, address);
        if (RPC_ACTIVE_COUNT.containsKey(key)) {
            RPC_ACTIVE_COUNT.put(key, RPC_ACTIVE_COUNT.get(key) + 1);
        } else {
            RPC_ACTIVE_COUNT.put(key, 1);
        }
    }

    /**
     * @return void
     * @Author mSc
     * @Description 活跃度-1
     * @Param [interfaceName, methodName, address]
     **/
    public synchronized static void deActivity(String interfaceName, String methodName, String address) {
        String key = generateKey(interfaceName, methodName, address);
        if (RPC_ACTIVE_COUNT.containsKey(key)) {
            RPC_ACTIVE_COUNT.put(key, RPC_ACTIVE_COUNT.get(key) - 1);
        }
    }

    /**
     * @return java.lang.String
     * @Author mSc
     * @Description 返回接口名+方法名+IP地址
     * @Param [interfaceName, methodName, address]
     **/
    private static String generateKey(String interfaceName, String methodName, String address) {
        return interfaceName + "." + methodName + "." + address;
    }
}
