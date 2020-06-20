package concurrent;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by mengtian on 2020/6/16
 */
public class PackUnPackTest {
    private static LinkedBlockingDeque<String> queue = new LinkedBlockingDeque();

    static class Producer extends Thread {
        private Thread consumer;

        public Producer(Thread consumer) {
            this.consumer = consumer;
        }

        @Override
        public void run() {
            while (true) {
                if (queue.isEmpty()) {
                    queue.add("data");
                    System.out.println("unpack consumer");
                    LockSupport.unpark(consumer);
                    LockSupport.park();
                }
                System.out.println("p running");
            }
        }
    }

    static class Consumer extends Thread {
        private Thread producer;

        public void setProducer(Thread producer) {
            this.producer = producer;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String take = queue.take();
                    System.out.println(take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (queue.size() == 0) {
                    System.out.println("pack");
                    LockSupport.unpark(producer);
                    LockSupport.park();
                }
                System.out.println("c running");
            }
        }
    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        Producer producer = new Producer(consumer);
        consumer.setProducer(producer);
        consumer.start();
        producer.start();
    }
}
