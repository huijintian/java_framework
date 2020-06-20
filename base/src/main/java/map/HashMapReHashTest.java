package map;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mengtian on 2020/5/29
 */
public class HashMapReHashTest {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>(16);
        int index = 0;
        for (; index < 300; index++) {
            map.put("index" + index, "value" + index);
        }

        System.out.println(map.size());
    }
}
