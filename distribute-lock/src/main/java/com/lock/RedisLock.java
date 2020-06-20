package com.lock;

/**
 * Created by mengtian on 2020/6/19
 */
public interface RedisLock {

    boolean lock(String key, String value, long expireTime);

    boolean unLock(String key, String value);
}
