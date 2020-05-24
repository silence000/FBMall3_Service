package com.excmmy.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
    }

    //配置文档信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("Excmmy", "https://www.excmmy.com", "silence000@foxmail.com");
        return new ApiInfo(
                "FBMall3_ReviewsServer接口文档", // 标题
                "FBMall3_Server", // 描述
                "v1.0", // 版本
                "https://www.excmmy.com", // 组织链接
                contact,
                "Apache 2.0 许可", // 许可
                "https://www.excmmy.com",
                new ArrayList<>()// 许可链接
        );
    }
}
