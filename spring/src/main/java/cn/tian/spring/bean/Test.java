package cn.tian.spring.bean;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by mengtian on 2018/4/26
 */
public class Test {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:bean/spring-bean.xml");
        MyBean bean = (MyBean) beanFactory.getBean("myBean");
        System.out.println(bean.getTestStr());

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader.getResource(""));
    }
}
