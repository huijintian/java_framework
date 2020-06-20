package concurrent;

/**
 * Created by mengtian on 2020/6/5
 */
public class SynchTest {
    private final static Object object = new Object();

    public static void main(String[] args) {
        synchronized (object) {
            System.out.println("hah");
        }
    }
}
