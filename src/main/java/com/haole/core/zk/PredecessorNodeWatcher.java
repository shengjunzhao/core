package com.haole.core.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.locks.Condition;

/**
 * Created by shengjunzhao on 2018/10/3.
 */
public class PredecessorNodeWatcher implements Watcher {

    private Condition condition;

    public PredecessorNodeWatcher(Condition condition) {
        this.condition = condition;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        //前序节点被删除，锁被释放，唤醒当前等待线程
        if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
            condition.signal();
        }

    }
}
