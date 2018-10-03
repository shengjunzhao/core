package com.haole.core.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

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
    }


}
