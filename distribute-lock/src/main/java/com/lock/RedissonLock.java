package com.lock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * Created by mengtian on 2020/6/19
 */
public class RedissonLock implements RedisLock {

    private static final RedissonClient client = null;

    public RedissonClient getClient() {
        if (client == null) {
            Config config = new Config();
            config.useSingleServer().setAddress("redis://127.0.0.1:6379");
            return Redisson.create(config);
        }
        return client;
    }

    @Override
    public boolean lock(String key, String value, long expireTime) {
        RedissonClient client = getClient();
        RLock lock = client.getLock(key);
        try {
            boolean res = lock.tryLock(1, expireTime, TimeUnit.MILLISECONDS);
            return res;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            lock.unlock();
        }
        return false;
    }

    @Override
    public boolean unLock(String key, String value) {
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        RedissonLock lock = new RedissonLock();
        String key = "lock:RedissonLock";
        boolean res = lock.lock(key, "", 100000);
        System.out.println(res);
    }
}
