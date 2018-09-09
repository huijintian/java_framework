package cn.tian.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by mengtian on 2018/9/9
 */
public class JedisUtil {
    public static Jedis getJedis() {
        return new Jedis("localhost");
    }

    private JedisUtil() {
    }
}
