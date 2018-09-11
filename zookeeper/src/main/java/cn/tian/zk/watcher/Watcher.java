package cn.tian.zk.watcher;

import org.apache.zookeeper.WatchedEvent;

/**
 * Created by mengtian on 2018/9/11
 */
public interface Watcher extends org.apache.zookeeper.Watcher {
    void process(WatchedEvent event);
}
