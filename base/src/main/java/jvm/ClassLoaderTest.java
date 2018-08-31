package jvm;

/**
 * Created by mengtian on 2018/7/19
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        do {
            System.out.println(classLoader.toString());
            classLoader = classLoader.getParent();
        } while (classLoader != null);
    }
}
