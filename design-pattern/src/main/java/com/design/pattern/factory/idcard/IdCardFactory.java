package com.design.pattern.factory.idcard;

import com.design.pattern.factory.framework.Factory;
import com.design.pattern.factory.framework.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengtian on 2020/6/23
 */
public class IdCardFactory extends Factory {

    private List owners = new ArrayList<>();

    @Override
    public Product createProduct(String owner) {
        return new IDCard(owner);
    }

    @Override
    public void registerProduct(Product product) {
        owners.add(product);
    }

    public List getOwners() {
        return owners;
    }
}
