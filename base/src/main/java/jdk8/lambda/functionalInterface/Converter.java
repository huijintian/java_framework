package jdk8.lambda.functionalInterface;

/**
 * Created by mengtian on 2018/8/3
 * <p>
 * 只包含了一个方法的接口可以用作lambda表达式
 * <p>
 * 加上@FunctionalInterface注解后，再添加第二个方法编译器会报异常
 */
@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);
}
