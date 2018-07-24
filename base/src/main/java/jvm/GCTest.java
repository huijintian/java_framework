package jvm;

/**
 * Created by mengtian on 2018/7/17
 */
public class GCTest {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
//        testAllocation();
        testPretenureSizeThreshold();
    }

    /**
     * Minor GC 测试
     * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    /*public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];//出现一次Minor GC
    }*/

    /**
     * 大对象直接进入老年代
     * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * -XX:PretenureSizeThreshold=3145728
     * PretenureSizeThreshold 不能像上面-Xms等那样设置为M
     * 3145728 = 3M
     */
    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB];//申请4M内存，直接分配在老年代中了
    }
}
