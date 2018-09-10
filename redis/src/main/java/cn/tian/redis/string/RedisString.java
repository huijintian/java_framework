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

    public void incr() {
        System.out.println("incr>>>>>");
        String key = "page_view";
        //init value
        System.out.println(jedis.set(key, "20"));
        System.out.println(jedis.incr(key));
    }

    /**
     * use redis incr to controller user api caller times
     */
    public void apiCallLimit(String ip) {
        //current seconds
        long time = System.currentTimeMillis() / 1000;
        String key = ip + ":" + time;

        String currentCount = jedis.get(key);
        Integer current = currentCount == null ? null : Integer.parseInt(currentCount);

        if (current == null) {
            jedis.set(key, "1");
            jedis.expire(key, 1);
        } else if (current < 10) {
            jedis.incr(key);
        } else if (current >= 10) {
            //some controller action
            System.err.println("too many requests per second");
        }
    }

    public void mSetGet() {
        System.out.println("mGet>>>>>");
        jedis.mset("redis", "redis.com", "mongodb", "mongodb.org");
        jedis.mget("redis", "mongodb").forEach(s -> System.out.println(s));
    }

    public void mSetNx() {
        System.out.println("mSetNx>>>>>");
        jedis.msetnx("mysql", "mysql.com", "java", "java.com");
        jedis.mget("mysql", "java").forEach(s -> System.out.println(s));
        Long msetnx = jedis.msetnx("sqlserver", "sqlserver.com", "mysql", "mysql.com");
        System.out.println("status:" + msetnx);
        System.out.println(jedis.get("sqlserver"));
    }

    public static void main(String[] args) {
        jedis.flushAll();
        RedisString redisString = new RedisString();
        redisString.append();
        redisString.bitCount();
        redisString.decr();
        redisString.decrby();
        redisString.getSet();
        redisString.incr();
        redisString.apiCallLimit("local");
        for (int i = 0; i < 20; i++) {
            redisString.apiCallLimit("local");
        }
        redisString.mSetGet();
        redisString.mSetNx();
        jedis.close();
    }
}
