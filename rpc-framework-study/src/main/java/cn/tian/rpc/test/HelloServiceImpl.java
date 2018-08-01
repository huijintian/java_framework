package cn.tian.rpc.test;

/**
 * Created by mengtian on 2018/8/2
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "Hello " + name;
    }
}
