package jvm;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * Created by mengtian on 2018/7/12
 * <p>
 * 借助CGLib使方法区出现内存溢出异常
 * VM Args:
 * JDK7  -XX:PermSize=10M -XX:MaxPermSize=10M
 * JDK8 -XX:MataspaceSize=10M -XX:MaxMataspaceSize=10M
 */
public class JavaMethodAreaOOM {
    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy)
                    -> methodProxy.invokeSuper(o, objects));
            enhancer.create();
        }
    }

    static class OOMObject {
    }
}
