package com.spring.data.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;

/**
 * Created by mengtian on 2018/6/7
 */
@SpringBootApplication
public class SampleRedisApplication implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate template;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("select keys.....");
        System.out.println(template.keys("stockZDF:historydata%*"));
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SampleRedisApplication.class, args);
        System.in.read();
    }
}
