package com.haole.core.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * ClassName: DistributedID
 * Description:  利用zookeeper生成分布式id，创建EPHEMERAL_SEQUENTIAL节点即可
 * Author: shengjunzhao
 * Date: 2018/9/28 19:58
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class DistributedID {

    private static final String root = "/haole/id";
    private static final String ID_NAME = "/did-";
    private ZooKeeper zk;

    public DistributedID(ZooKeeper zk) throws KeeperException, InterruptedException {
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
                        zk.create(sb.toString(), "i".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
            }
        }
    }

    public long getId(String bussiness) throws KeeperException, InterruptedException {
        long id = 0L;
        String path = root + "/" + bussiness;
        System.out.println(path);
        Stat stat = zk.exists(path, false);
        if (null == stat)
            zk.create(path, "i".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        path = path + ID_NAME;
        String value = zk
                .create(path, "i".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        id = Long.valueOf(value.substring(path.length())).longValue();

        return id;
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        //        String root1 = "/haole/id";
        //        String[] s = root1.split("\\/");
        //        System.out.println(s[1]);
//        ZooKeeper zk = new ZooKeeper("10.37.147.250:12181", 500000, null);
        ZooKeeper zk = new ZooKeeper("192.168.209.132:2181", 500000, null);
        DistributedID id = new DistributedID(zk);
        System.out.println(id.getId("snow"));
        System.out.println(id.getId("snow"));
        zk.close();

    }

}
