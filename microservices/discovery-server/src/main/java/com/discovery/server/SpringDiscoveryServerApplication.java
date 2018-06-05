package com.discovery.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by mengtian on 2018/6/5
 */
@SpringBootApplication
@EnableEurekaServer
public class SpringDiscoveryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDiscoveryServerApplication.class, args);
    }
}
