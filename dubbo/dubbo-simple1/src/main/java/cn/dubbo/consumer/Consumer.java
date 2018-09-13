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
        for (int i = 0; i < 10; i++) {
            demoService.sayHello("hello, i num " + i);
        }
        for (int i = 0; i < 10; i++) {
            demoService.sayHello("hello, i num " + i);
        }
        /*String hello = demoService.sayHello("world");
        System.out.println(hello);*/
    }
}
