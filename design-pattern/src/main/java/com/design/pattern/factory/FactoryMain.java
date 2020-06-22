package com.design.pattern.factory;

import com.design.pattern.factory.framework.Factory;
import com.design.pattern.factory.framework.Product;
import com.design.pattern.factory.idcard.IdCardFactory;

/**
 * Created by mengtian on 2020/6/23
 */
public class FactoryMain {
    public static void main(String[] args) {
        Factory factory = new IdCardFactory();
        Product p1 = factory.create("x");
        Product p2 = factory.create("y");
        Product p3 = factory.create("z");
        p1.use();
        p2.use();
        p3.use();
    }
}
