package cn.dubbo.consumer;

import cn.dubbo.service.HelloService;
import com.alibaba.dubbo.rpc.service.EchoService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by mengtian on 2018/3/16
 */
public class ConsumerTest {

    @Test
    public void serviceCheckTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("" +
                "consumer.xml");
        context.start();

        HelloService demoService = (HelloService) context.getBean("helloService");
        String hello = demoService.sayHello("world");
        System.out.println(hello);
    }

    @Test
    public void echoService() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("" +
                "consumer.xml");
        context.start();

        EchoService echoService = (EchoService)context.getBean("helloService");
        String status = (String) echoService.$echo("OK");
        assert(status.equals("OK"));
    }
}
