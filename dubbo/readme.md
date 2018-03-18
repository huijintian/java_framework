#### 配置：
    
   配置地址224-239区间
   ![](http://mengtian123.oss-cn-shenzhen.aliyuncs.com/18-3-7/17344292.jpg)


#### 异常：
    
1.  Can't assign requested address

    Dubbo的广播模式下Can't assign requested address问题解决
    (http://blog.csdn.net/garylongqq/article/details/52014617)
    
    即添加-Djava.net.preferIPv4Stack=true  
    ![](http://mengtian123.oss-cn-shenzhen.aliyuncs.com/18-3-7/40527853.jpg)
    


#### 示例

1.  启动时检查
    ```
    <dubbo:reference id="helloService" interface="cn.dubbo.service.HelloService" check="true"/>
    ```
    此配置会在启动时检查服务提供方是否在线，不存在则报错
    
    Failed to check the status of the service cn.dubbo.service.HelloService
    
    当配置check = false时
    ```
        <dubbo:reference id="helloService" interface="cn.dubbo.service.HelloService" check="false"/>
    ```
    应用启动会向服务地址请求服务，不存在时报错：
    com.alibaba.dubbo.rpc.RpcException: Failed to invoke the method sayHello in the service 

2.  