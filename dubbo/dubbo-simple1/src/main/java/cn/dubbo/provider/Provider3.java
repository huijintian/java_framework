package cn.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by mengtian on 2018/9/13
 */
public class Provider3 {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "provider3.xml");

        context.start();
        System.in.read();
    }
}
