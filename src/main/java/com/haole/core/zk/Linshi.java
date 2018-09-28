package com.haole.core.zk;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by shengjunzhao on 2018/9/25.
 */
public class Linshi {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        Watcher watcher = new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {
                System.out.println("event:" + event.toString());
            }
        };

        Watcher watcher1 = (WatchedEvent event) -> {
            System.out.println("=======event:" + event.toString());
        };

//        ZooKeeper zk = new ZooKeeper("192.168.209.132:2181", 500000, watcher1);
        ZooKeeper zk = new ZooKeeper("10.37.147.250:12181", 500000, watcher1);

        //创建一个节点root，数据是mydata,不进行ACL权限控制，节点为永久性的(即客户端shutdown了也不会消失)
//        zk.create("/haole", "haole".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        zk.create("/haole/test", "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        zk.create("/haole/test/app", "mydata".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println(zk.getChildren("/haole/test/app", true));
        System.out.println("****create childone");
        //在root下面创建一个childone znode,数据为childone,不进行ACL权限控制，节点为永久性的
        // 执行了watch
        zk.create("/haole/test/app/childone", "childone".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("****create childtwo");
        // 未执行watch
        zk.create("/haole/test/app/childtwo", "childtwo".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//取得/root节点下的子节点名称,返回List<String>
//        List<String> children = zk.getChildren("/haole", true);
//        System.out.println(children);

//取得/root/childone节点下的数据,返回byte[]
//        byte[] data = zk.getData("/haole/test/app/childone", true, null);
//        System.out.println(new String(data));

//修改节点/root/childone下的数据，第三个参数为版本，如果是-1，那会无视被修改的数据版本，直接改掉
//        zk.setData("/haole/test/app/childone", "childonemodify1".getBytes(), -1);

//删除/root/childone这个节点，第二个参数为版本，－1的话直接删除，无视版本
//        zk.delete("/haole/test/app/childone", -1);

//        zk.create("/haole/test/app/testChildPath1","1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
//        zk.create("/haole/test/app/testChildPath2","2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
//        zk.create("/haole/test/app/testChildPath3","3".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
//        zk.create("/haole/test/app/testChildPath4","4".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);

//        zk.create("/haole/test/app/testChildPath","1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
//        zk.create("/haole/test/app/testChildPath","2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
//        zk.create("/haole/test/app/testChildPath","3".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
//        zk.create("/haole/test/app/testChildPath","4".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);

//        System.out.println(zk.getChildren("/haole/test/app", true));
//关闭session
        zk.close();
    }
}
