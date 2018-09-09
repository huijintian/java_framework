package cn.tian.redis.key;

import cn.tian.redis.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

/**
 * Created by mengtian on 2018/9/9
 */
public class RedisKey {
    private static Jedis jedis = JedisUtil.getJedis();

    public void del() {
        System.out.println("single key del >>>");
        jedis.set("name", "mengtian");
        //del single key
        System.out.println(jedis.del("name"));
        //del not exist key
        System.out.println(jedis.del("phone"));
    }

    public void delMutil() {
        System.out.println("muti key del>>>>>");
        jedis.set("type", "key-value store");
        jedis.set("website", "redis.com");
        System.out.println(jedis.del("type", "website"));
    }

    public void exists() {
        System.out.println("exists>>>>>");
        jedis.set("db", "redis");
        System.out.println(jedis.exists("db"));
        jedis.del("db");
        System.out.println(jedis.exists("db"));
    }

    public void expire() {
        System.out.println("expire>>>>>");
        String key = "cache_page";
        jedis.set(key, "google.com");
        jedis.expire(key, 10);
        System.out.printf("%s, expire:%s%n", jedis.get(key), jedis.exists(key));
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s, expire:%s%n", jedis.get(key), jedis.exists(key));
    }

    public void setExpireLockKey() {
        System.out.println("lock expire>>>>>");
        // use in distribute system lock key
        String set = jedis.set("lock", "expireLockKey", "NX", "PX", 10000);
        System.out.println(set);
        System.out.println(jedis.get("lock"));
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s, expire:%s%n", jedis.get("lock"), jedis.exists("lock"));
    }

    public void sort() {
        System.out.println("sort>>>>>");
        String key = "today_cost";
        jedis.lpush(key, "30", "1.5", "10", "8");
        jedis.sort(key).forEach(s -> System.out.println(s));//sort will not change key values
        jedis.sort(key, new SortingParams().desc(), "today_cost_sort");//this will change value index
    }

    public void outKeySort() {
        System.out.println("out key sort>>>>>");
        String uidKey = "uid";
        String userName = "user_name_";
        String levelKey = "user_level_";

        //init value
        jedis.lpush(uidKey, "1", "2", "3");
        jedis.set(userName + "1", "admin");
        jedis.set(userName + "2", "jack");
        jedis.set(userName + "3", "peter");

        jedis.set(levelKey + "1", "9999");
        jedis.set(levelKey + "2", "10");
        jedis.set(levelKey + "3", "25");

        //sort uid
        System.out.println("sort uid:");
        jedis.sort(uidKey).forEach(s -> System.out.print(s + ","));
        //sort uid by user_level
        System.out.println("\n sort uid by user_level:");
        jedis.sort(uidKey, new SortingParams().by("user_level_*")).forEach(s -> System.out.print(s + ","));
        //sort uid by user_level and get the user_name result
        System.out.println("\n sort uid by user_level and get the user_name result:");
        jedis.sort(uidKey, new SortingParams().by("user_level_*").get("user_name_*")).forEach(s -> System.out.print(s + ","));
        //sort uid and get uid, user_name, user_level
        System.out.println("\n sort uid and get uid, user_name, user_level:");
        //get("#") means get the sort value self
        jedis.sort(uidKey, new SortingParams().get("#").get("user_name_*").get("user_level_*"))
                .forEach(s -> System.out.print(s + ","));
    }

    public static void main(String[] args) {
        RedisKey redisKey = new RedisKey();
        //!!! it will clear all data in redis
        jedis.flushAll();
        redisKey.del();
        redisKey.delMutil();
        redisKey.exists();
        redisKey.expire();
        redisKey.setExpireLockKey();
        redisKey.sort();
        redisKey.outKeySort();
        jedis.close();
    }
}
