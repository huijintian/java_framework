package threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mengtian on 2018/8/2
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        Executor executor = new ThreadPoolExecutor(5,
                10,
                1000l,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(5),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 0; i < 15; i++) {
            executor.execute(new A(i));
        }
    }

    private static class A implements Runnable {
        private int num;

        public A(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread num:" + num);
        }
    }
}
