package concurrent;

/**
 * Created by mengtian on 2018/7/19
 * <p>
 * 对一个线程调用两次start操作
 * <p>
 * 出现异常：
 * Exception in thread "main" java.lang.IllegalThreadStateException
 * at java.lang.Thread.start(Thread.java:705)
 * at concurrent.ThreadTest.main(ThreadTest.java:12)
 * hello world
 * <p>
 * 原因 线程是具有状态的，已经开始的线程不能够再次start
 */
public class ThreadTest {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("hello world");
        });
        thread.start();
        thread.start();
    }
}
