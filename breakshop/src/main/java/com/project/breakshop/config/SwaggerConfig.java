package com.project.breakshop.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .version("v1.0.0")
                .title("BreakShop")
                .description("Mysql & Redis 기반, Spring boot 으로 만들어진 배달앱 백엔드 ");

        return new OpenAPI().info(info);
    }
}

