package cn.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by mengtian on 2018/3/16
 */
public class Provider1 {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "provider1.xml");

        context.start();
        System.in.read();
    }
}
