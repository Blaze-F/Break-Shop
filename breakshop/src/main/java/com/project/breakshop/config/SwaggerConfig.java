package com.project.breakshop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
    @OpenAPIDefinition(
            info = @Info(title = "배달앱 서비스 명세서",
                    description = "Redis & Mysql 기반 라이더 배차 & 배달앱 서비스 API 명세서",
                    version = "v1"))
    @RequiredArgsConstructor
    @Configuration
    public class SwaggerConfig {

        @Bean
        public GroupedOpenApi chatOpenApi() {
            String[] paths = {"/v1/**"};

            return GroupedOpenApi.builder()
                    .group("배달앱 API v1")
                    .pathsToMatch(paths)
                    .build();
        }
    }
