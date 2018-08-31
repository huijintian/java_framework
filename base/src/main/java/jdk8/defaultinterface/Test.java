package jdk8.defaultinterface;

/**
 * Created by mengtian on 2018/8/3
 */
public class Test {
    public static void main(String[] args) {
        Formula formula = new Formula() {
            @Override
            public double calculate(int num) {
                return sqrt(num * 100);
            }
        };

        System.out.println(formula.calculate(100));
        System.out.println(formula.sqrt(9));
    }
}
