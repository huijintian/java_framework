package com.design.pattern.adapter;

/**
 * Created by mengtian on 2020/6/22
 */
public class AdapterMain {
    public static void main(String[] args) {
        Print p = new PrintBanner();
        p.printWeak();
        p.printStrong();
    }
}
