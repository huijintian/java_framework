package distributedLock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

/**
 * Created by mengtian on 2018/7/5
 */
public class DistributedLock {
    private static final String LOCK_SUCCESS = "OK";
    /**
     * 如果key不存在则插入该key对应的value并返回OK，否则什么都不做返回null
     */
    private static final String SET_IF_NOT_EXIST = "NX";
    /**
     * 设置过期时间为毫秒
     */
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * @param jedisPool
     * @param lockKey
     * @param requestId
     * @param expireTime
     */
    private static void validParam(JedisPool jedisPool, String lockKey,
                                   String requestId, int expireTime) {
        if (null == jedisPool) {
            throw new IllegalArgumentException("jedisPool obj is null");
        }

        if (null == lockKey || "".equals(lockKey)) {
            throw new IllegalArgumentException("lock key  is blank");
        }

        if (null == requestId || "".equals(requestId)) {
            throw new IllegalArgumentException("requestId is blank");
        }

        if (expireTime < 0) {
            throw new IllegalArgumentException("expireTime is not allowed less zero");
        }
    }

    public static boolean tryLock(JedisPool jedisPool, String lockKey,
                                  String requestId, int expireTime) {
        validParam(jedisPool, lockKey, requestId, expireTime);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //redis的这个方法保证了操作的原子性
            String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME);
            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return false;
    }

    public static void lock(JedisPool jedisPool, String lockKey,
                            String requiredId, int expireTime) {
        validParam(jedisPool, lockKey, requiredId, expireTime);
        //自旋转重试获取锁，类似CAS
        while (true) {
            if (tryLock(jedisPool, lockKey, requiredId, expireTime)) {
                return;
            }
        }
    }

    public static boolean unLock(JedisPool jedisPool, String lockKey, String requestId) {
        validParam(jedisPool, lockKey, requestId, 1);

        //lua脚本，
        // redis.call('get', KEYS[1]) 为 获取key对应的value值，
        // ... == ARGV[1] 判断当前传入的requestId是否等于前面取出的value
        // 相等则执行 redis.call('del', KEYS[1]) 删除对应key，释放锁
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Object result = jedis.eval(script, Collections.singletonList(lockKey),
                    Collections.singletonList(requestId));

            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return false;
    }
}
