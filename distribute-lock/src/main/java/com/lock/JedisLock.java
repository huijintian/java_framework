package com.lock;

import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * Created by mengtian on 2020/6/19
 */
public class JedisLock implements RedisLock {

    public static Jedis getJedis() {
        return new Jedis("localhost");
    }

    @Override
    public boolean lock(String key, String value, long expireTime) {
        Jedis jedis = getJedis();
        try {
            String res = jedis.set(key, value, "NX", "PX", expireTime);
            if ("OK".equals(res)) {
                return true;
            }
            return false;
        } finally {
            jedis.close();
        }
    }

    private static final Long RELEASE_SUCCESS = 1L;

    @Override
    public boolean unLock(String key, String value) {
        Jedis jedis = getJedis();
        try {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        } finally {
            jedis.close();
        }
    }

    public static void main(String[] args) {
        String key = "lock:jedisLock";
        String value = System.currentTimeMillis() + "";
        JedisLock jedisLock = new JedisLock();
        boolean lock = jedisLock.lock(key, value, 100000);
        System.out.println(lock);
//        lock = jedisLock.unLock(key, value);
        System.out.println(lock);
    }
}
