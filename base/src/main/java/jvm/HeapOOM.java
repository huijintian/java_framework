package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Java堆内存溢出异常测试
 * <p>
 * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * Created by mengtian on 2018/7/10
 */
public class HeapOOM {
    static class OOMObject {
    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
