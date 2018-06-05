package com.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by mengtian on 2018/6/5
 */
@SpringBootApplication
@EnableEurekaClient
public class SpringClientEmployeeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringClientEmployeeServiceApplication.class, args);
    }
}
