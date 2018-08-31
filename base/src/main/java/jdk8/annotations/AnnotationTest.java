package jdk8.annotations;

/**
 * Created by mengtian on 2018/8/28
 */
public class AnnotationTest {
    public static void main(String[] args) {
        Hint hint = Person.class.getAnnotation(Hint.class);
        System.out.println(hint);

        Hint[] hints2 = Person.class.getAnnotationsByType(Hint.class);
        System.out.println(hints2.length);
    }
}
