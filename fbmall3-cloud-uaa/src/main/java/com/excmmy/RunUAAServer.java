package com.excmmy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan(basePackages = "com.excmmy.mapper")
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients
public class RunUAAServer {
    public static void main(String[] args) {
        SpringApplication.run(RunUAAServer.class);
    }
}
