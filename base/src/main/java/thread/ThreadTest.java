package thread;

/**
 * Created by mengtian on 2020/6/15
 */
public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("thread is running.");
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.setDaemon(true);
        t.start();
        t.stop();
        t.join();
        t.interrupt();
        System.exit(0);
    }
}
