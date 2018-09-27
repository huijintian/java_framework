package cn.tian.redis.hash;

import cn.tian.redis.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * Created by mengtian on 2018/9/18
 */
public class RedisHash {
    private static Jedis jedis = JedisUtil.getJedis();

    public void hdel() {
        System.out.println("hdel>>>>>");
        String key = "abbr";
        jedis.hset(key, "a", "apple");
        jedis.hset(key, "b", "banana");
        jedis.hset(key, "cat", "cat");
        jedis.hset(key, "d", "dog");
        jedis.hgetAll(key).entrySet().stream().forEach(s -> System.out.println(s.getKey() + ":" + s.getValue()));
        System.out.println(jedis.hdel(key, "a"));
        System.out.println(jedis.hdel(key, "not-exists"));
        jedis.hgetAll(key).entrySet().stream().forEach(s -> System.out.println(s.getKey() + ":" + s.getValue()));
    }

    public static void main(String[] args) {
        RedisHash hash = new RedisHash();
        hash.hdel();
    }
}
