package concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by mengtian on 2020/5/10
 * 基于ReentrantLock模拟出现死锁，超时后自动释放锁的操作
 */
public class InterruptiblyLock {
    public ReentrantLock lock1 = new ReentrantLock();
    public ReentrantLock lock2 = new ReentrantLock();

    /**
     * 先获取 lock1，再获取lock2
     *
     * @return
     */
    public Thread lock1() {
        Thread t = new Thread(() -> {
            try {
                lock1.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock2.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + "，执行完毕！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
                if (lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                }
            }
        });
        t.start();
        return t;
    }

    /**
     * 先获取 lock2，再获取lock1
     *
     * @return
     */
    public Thread lock2() {
        Thread t = new Thread(() -> {
            try {
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock1.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + "，执行完毕！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
                if (lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                }
            }
        });
        t.start();
        return t;
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        InterruptiblyLock lock = new InterruptiblyLock();
        Thread thread1 = lock.lock1();
        Thread thread2 = lock.lock2();
        while (true) {
            if (System.currentTimeMillis() - time >= 3000) {
                thread2.interrupt();
            }
        }
    }
}
