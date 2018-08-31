package jdk8.lambda;

import jdk8.lambda.functionalInterface.Converter;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by mengtian on 2018/8/3
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("collection:");
        collection();
        System.out.println("functionalInterface:");
        functionalInterface();
        System.out.println("predicate:");
        predicate();
        System.out.println("function:");
        function();
        System.out.println("consumer:");
        consumer();
        System.out.println("optional:");
        optional();
    }

    //lambda 基础用法
    public static void collection() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xien");

        //no lambda
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        System.out.println(names);

        //with lambda
        Collections.sort(names, (a, b) -> b.compareTo(a));
    }

    //functionalInterface接口使用
    public static void functionalInterface() {
        //第一种用法
        Converter<String, Integer> converter = from -> Integer.valueOf(from);
        //使用静态方法引用的方式
        converter = Integer::valueOf;
        Integer converted = converter.convert("123");
        System.out.println(converted);


    }

    public static void predicate() {
        Predicate<String> predicate = s -> s.length() > 0;

        System.out.println(predicate.test("foo"));
        System.out.println(predicate.negate().test("foo"));

        Predicate<Boolean> isNull = Objects::isNull;
        Predicate<Boolean> NotNul = Objects::nonNull;

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
    }

    public static void function() {
        Function<String, Integer> toInteger = Integer::valueOf;

        Function<String, String> backToString = toInteger.andThen(String::valueOf);

        System.out.println(backToString.apply("123"));
    }

    public static void suppliers() {
        Supplier<Person> personSupplier = Person::new;
        personSupplier.get();
    }

    public static void consumer() {
        Consumer<Person> greeter = p -> System.out.println("Hello, " + p.getFirstName());
        greeter.accept(new Person("tom", "123"));
    }

    public static void optional() {
        Optional<String> optional = Optional.of("bam");

        System.out.println(optional.isPresent());
        System.out.println(optional.get());
        System.out.println(optional.orElse("fallback"));

        optional.ifPresent(s -> System.out.println(s.charAt(0)));
    }

    static class Person {
        private String firstName;
        private String lastName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Person() {
        }

        public Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}
