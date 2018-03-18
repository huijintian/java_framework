package cn.dubbo.provider;

import cn.dubbo.service.HelloService;

/**
 * Created by mengtian on 2018/3/16
 */
public class HelloServiceImpl implements HelloService {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
