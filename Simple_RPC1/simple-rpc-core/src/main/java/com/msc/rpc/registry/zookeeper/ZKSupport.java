package com.msc.rpc.registry.zookeeper;

import com.msc.rpc.common.enumeration.ExceptionEnum;
import com.msc.rpc.common.exception.RPCException;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.registry.zookeeper
 * @Description: zookeeper注册中心的支持类，提供以下功能:
 * 1）连接zookeeper注册中心服务器
 * 2）创建Znode节点
 * 3）创建ZK路径
 * 4）关闭与zookeeper服务器的连接
 */
@Slf4j
public class ZKSupport {
    protected ZooKeeper zooKeeper = null;
    //通过CountDownLatch的使用，控制线程的执行顺序
    private volatile CountDownLatch countDownLatch = new CountDownLatch(1);

    private static final int ZK_SESSION_TIME = 5000;

    /**
     * @return void
     * @Author mSc
     * @Description 通过address进行连接
     * @Param [address]
     **/
    public void connect(String address) {
        try {
            this.zooKeeper = new ZooKeeper(address, ZK_SESSION_TIME, (WatchedEvent watchedEvent) -> {
                Watcher.Event.KeeperState keeperState = watchedEvent.getState();
                Watcher.Event.EventType eventType = watchedEvent.getType();
                //如果是建立连接
                if (keeperState == Watcher.Event.KeeperState.SyncConnected) {
                    //连接建立成功
                    if (eventType == Watcher.Event.EventType.None) {
//                        countDown() 计时唤醒
                        countDownLatch.countDown();
                        log.info("ZK建立连接");
                    }
                }
            });
            log.info("开始连接ZK服务器");
//             .await() 的线程阻塞
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @Author mSc
     * @Description 如果不存在则创建路径
     * @Param [path, createMode]
     **/
    public void createPathIfAbsent(String path, CreateMode createMode) throws KeeperException, InterruptedException {
        String[] split = path.split("/");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (StringUtils.hasText(split[i])) {
                Stat stat = zooKeeper.exists(stringBuilder.toString(), false);
                if (stat == null) {
                    zooKeeper.create(stringBuilder.toString(), new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
                }
            }
            if (i < split.length - 1) {
                stringBuilder.append("/");
            }
        }
    }

    /**
     * @return void
     * @Author mSc
     * @Description 如果不存在则创建节点
     * @Param [data, path]
     **/
    public void createNodeIfAbsent(String data, String path) {
        try {
            byte[] bytes = data.getBytes(Charset.forName("UTF-8"));
            //建立临时节点
            zooKeeper.create(path + "/" + data, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (KeeperException e) {
            if (e instanceof KeeperException.NodeExistsException) {
                throw new RPCException(ExceptionEnum.REGISTRY_ERROR, "ZK路径" + path + "下已经存在节点" + data);
            } else {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.getCause();
        }
    }

    /**
     * @return void
     * @Author mSc
     * @Description 关闭
     * @Param []
     **/
    public void close() throws InterruptedException {
        if (zooKeeper != null) {
            zooKeeper.close();
        }
    }

    /**
     * @return java.util.List<java.lang.String>
     * @Author mSc
     * @Description 获取子节点列表
     * 如果watcher不为null，并且调用成功（没有异常），会将watcher注册在指定的path上。当path（父节点）被删除或者path下面创建/删除子节点，将触发通知watcher。
     * @Param [address, watcher]
     **/
    public List<String> getChildren(String address, Watcher watcher) throws KeeperException, InterruptedException {
        return zooKeeper.getChildren(address, watcher);
    }

    /**
     * @return byte[]
     * @Author mSc
     * @Description Watch是ZooKeeper中非常重要的一个机制，它可以监控ZooKeeper中节点的变化情况，告知客户端。
     * @Param [address, watcher]
     **/
    public byte[] getData(String address, Watcher watcher) throws KeeperException, InterruptedException {
        return zooKeeper.getData(address, watcher, null);
    }
}
