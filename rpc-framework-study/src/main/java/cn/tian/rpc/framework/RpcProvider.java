package cn.tian.rpc.framework;

import cn.tian.rpc.test.HelloService;
import cn.tian.rpc.test.HelloServiceImpl;

/**
 * 暴露服务
 * Created by mengtian on 2018/8/2
 */
public class RpcProvider {

    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }
}
