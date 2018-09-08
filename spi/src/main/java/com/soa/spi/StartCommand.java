package com.soa.spi;

/**
 * Created by mengtian on 2018/9/8
 */
public class StartCommand implements Command{

    @Override
    public void execute() {
        System.out.println("start......");
    }
}
