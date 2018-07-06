package list;

import org.junit.Test;

import java.util.*;

/**
 * Created by mengtian on 2018/6/12
 */
public class RandomTest {
    @Test
    public void test() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(4);
        Collections.shuffle(list);
        System.out.println(list.get(0));
    }

    @Test
    public void test2() {
        int i = 4;
        switch (i) {
            case 1:
                System.out.println(1);
            case 2:
                System.out.println(2);
            default:
                System.out.println(4);
        }
    }
}
