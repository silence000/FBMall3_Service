package com.excmmy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients
@EnableSwagger2
public class RunOrdersServer {
    public static void main(String[] args) {
        SpringApplication.run(RunOrdersServer.class);
    }
}
