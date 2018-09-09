package cn.tian.redis.string;

import cn.tian.redis.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * Created by mengtian on 2018/9/9
 */
public class RedisString {
    private static Jedis jedis = JedisUtil.getJedis();

    public void append() {
        System.out.println("append>>>>>");
        String key = "ts";
        System.out.println(jedis.append(key, "0043"));
        System.out.println(jedis.append(key, "0035"));
        System.out.println(jedis.getrange(key, 0, 3));
    }

    public void bitCount() {
        System.out.println("bitCount>>>>>");
        String key = "bits";
        System.out.println(jedis.bitcount(key));
        System.out.println(jedis.setbit(key, 0l, "1"));
        System.out.println(jedis.bitcount(key));
    }

    public void decr() {
        System.out.println("decr>>>>>");
        //if key not exists, redis will init key a default value: 0
        String key = "failure_times";
        //init value
        jedis.set(key, "10");
        //decr
        System.out.println(jedis.decr(key));
        System.out.println(jedis.decr(key));
        System.out.println(jedis.decr(key));
    }

    public void decrby() {
        System.out.println("decrby>>>>>");
        String key = "count";
        //init value
        jedis.set(key, "100");
        //decrby
        System.out.println(jedis.decrBy(key, 15));
    }

    public void getSet() {
        System.out.println("getset>>>>>");
        String key = "db";
        //db not in redis, will return nil/null
        System.out.println(jedis.getSet(key, "mongodb"));
        System.out.println(jedis.getSet(key, "mysql"));
    }

    public static void main(String[] args) {
        jedis.flushAll();
        RedisString redisString = new RedisString();
        redisString.append();
        redisString.bitCount();
        redisString.decr();
        redisString.decrby();
        redisString.getSet();
        jedis.close();
    }
}
