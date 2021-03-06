package com.haole.core.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
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
        if (null == stat) {
            String bussinessNode = zk.create(path, "l".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("==== business node:" + bussinessNode);
        }
        String lockPath = path + LOCK_NAME;
        String node = zk.create(lockPath, "l".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        for (; ; ) {
            try {
                localLock.lock();
                List<String> childrenNodes = zk.getChildren(path, false);
                childrenNodes.sort((s1, s2) -> s1.compareTo(s2));
                String lastNode = node.substring(node.lastIndexOf("/") + 1);
                int currentIndex = childrenNodes.indexOf(lastNode);
                int prevIndex = currentIndex - 1;
                stat = null;
                while (prevIndex >= 0) {
                    stat = zk.exists(path + "/" + childrenNodes.get(prevIndex), new PredecessorNodeWatcher(condition));
                    if (stat != null) break;
                }
                if (null == stat) { // 获取到锁
                    LockData lockData = new LockData(new AtomicInteger(1), node);
                    threadLocal.set(lockData);
                    return true;
                } else { //前序节点存在，等待前序节点被删除，释放锁
                    condition.await();
                    return true;
                }
            } finally {
                localLock.unlock();
            }
        }
    }

    public void unlock() throws KeeperException, InterruptedException {
        LockData lockData = threadLocal.get();
        if (null == lockData)
            return;
        if (lockData.hits.decrementAndGet() == 0) {
            threadLocal.remove();
            zk.delete(lockData.node, -1);
            System.out.println("===== romove lock:" + lockData.node);
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

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("192.168.209.132:2181", 500000, null);
        DistributedLock dlock = new DistributedLock(zk);
        try {
            dlock.lock("pay");
            System.out.println("test distribute lock");
        } finally {
            dlock.unlock();
        }
        zk.close();
    }


}

