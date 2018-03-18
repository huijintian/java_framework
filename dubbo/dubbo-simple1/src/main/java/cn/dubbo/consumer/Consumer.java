package cn.dubbo.consumer;

import cn.dubbo.service.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by mengtian on 2018/3/16
 */
public class Consumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("" +
                "consumer.xml");
        context.start();

        HelloService demoService = (HelloService) context.getBean("helloService");
        String hello = demoService.sayHello("world");
        System.out.println(hello);
    }
}
