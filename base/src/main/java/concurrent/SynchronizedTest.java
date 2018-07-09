package concurrent;

/**
 * Created by mengtian on 2018/6/30
 */
public class SynchronizedTest {
    public static void main(String[] args) {
        synchronized (SynchronizedTest.class){
            System.out.println("Synchronize");
        }
    }
}
