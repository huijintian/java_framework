package com.design.pattern.adapter;

/**
 * Created by mengtian on 2020/6/22
 */
public class PrintBanner extends Print {
    private Banner banner;

    @Override
    public void printWeak() {
        banner.showWithParen();
    }

    @Override
    public void printStrong() {
        banner.showWithAster();
    }
}
