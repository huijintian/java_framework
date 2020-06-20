package com.design.pattern.sington;

/**
 * Created by mengtian on 2020/6/15
 * DCL（Double checking Locking）
 */
public class Sington {
    private static volatile Sington instance;

    public static Sington getInstance() {
        if (instance == null) {
            synchronized (Sington.class) {
                if (instance == null) {
                    instance = new Sington();
                }
            }
        }
        return instance;
    }
}
