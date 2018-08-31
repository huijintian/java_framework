package jdk8.defaultinterface;

/**
 * Created by mengtian on 2018/8/3
 */
public interface Formula {
    double calculate(int num);

    default double sqrt(int num) {
        return Math.sqrt(num);
    }
}
