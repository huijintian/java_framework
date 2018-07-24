package collection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by mengtian on 2018/7/21
 * <p>
 * 最久未使用算法（LRU）：最久没有访问的内容作为替换对象
 * <p>
 * LinkedHashMap的get方法实现Cache的使用方式，即将最近使用过的数据移动到队列的头部
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private int cacheSize;

    public LRUCache(int cacheSize) {
        super(16, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() >= cacheSize;
    }

    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCache<>(16);
        cache.put("abc", "first one");
        cache.put("123", "haha");
        cache.put("456", "oh");
        cache.put("789", "last one");

        System.out.println(cache.get("123"));
        System.out.println("-----------");
    }
}
