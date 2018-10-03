package com.haole.core.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName: DistributedID
 * Description:  利用zookeeper生成分布式锁
 * Author: shengjunzhao
 * Date: 2018/10/3 15:08
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class DistributedLock {

    private static final String root = "/haole/lock";
    private static final String LOCK_NAME = "/lock-";
    private ZooKeeper zk;
    private static ThreadLocal<LockData> threadLocal = new ThreadLocal<LockData>();
    private final Lock localLock;
    private final Condition condition;

    public DistributedLock(ZooKeeper zk) throws KeeperException, InterruptedException {
        this.zk = zk;
        Stat stat = zk.exists(root, false);
        if (null == stat) {
            String[] split = root.split("\\/");
            StringBuilder sb = new StringBuilder();
            for (String s : split) {
                if (!"".equals(s)) {
                    sb.append("/").append(s);
                    stat = zk.exists(sb.toString(), false);
                    if (null == stat)
                        zk.create(sb.toString(), "l".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
            }
        }
        localLock = new ReentrantLock();
        condition = localLock.newCondition();
    }

    public boolean lock(String bussiness) throws KeeperException, InterruptedException {
        //重入检测
        if (checkReentrant()) {
            return true;
        }
        String path = root + "/" + bussiness;
        Stat stat = zk.exists(path, false);
        if (null == stat)
            zk.create(path, "l".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        path = path + LOCK_NAME;
        String node = zk.create(path, "l".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        try {
            localLock.lock();
            List<String> childrenNodes = zk.getChildren(path, false);
            childrenNodes.sort((s1, s2) -> s1.compareTo(s2));
            int currentIndex = childrenNodes.indexOf(node);
            int prevIndex = currentIndex - 1;
            stat = null;
            while (prevIndex >= 0) {
                stat = zk.exists(childrenNodes.get(prevIndex), new PredecessorNodeWatcher(condition));
                if (stat != null) break;
            }
            if (null == stat) { // 获取到锁
                LockData lockData = new LockData(new AtomicInteger(1), node);
                threadLocal.set(lockData);
            } else { //前序节点存在，等待前序节点被删除，释放锁
                condition.await();
            }
        } finally {
            localLock.unlock();
        }
        return false;
    }

    public void unlock() throws KeeperException, InterruptedException {
        LockData lockData = threadLocal.get();
        if (null == lockData)
            return;
        if (lockData.hits.decrementAndGet() == 0) {
            threadLocal.remove();
            zk.delete(lockData.node, -1);
        }
    }


    private boolean checkReentrant() {
        LockData lockData = threadLocal.get();
        if (null != lockData) {
            lockData.hits.incrementAndGet();
            return true;
        }
        return false;
    }

    private static class LockData {
        final AtomicInteger hits;
        final String node;

        private LockData(AtomicInteger hits, String node) {
            this.hits = hits;
            this.node = node;
        }
    }


}

