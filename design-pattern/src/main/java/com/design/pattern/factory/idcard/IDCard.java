package com.design.pattern.factory.idcard;

import com.design.pattern.factory.framework.Product;

/**
 * Created by mengtian on 2020/6/23
 */
public class IDCard extends Product {
    private String owner;

    public IDCard(String owner) {
        this.owner = owner;
    }

    @Override
    public void use() {
        System.out.println("使用" + owner + "的ID卡");
    }

    public String getOwner() {
        return owner;
    }
}
