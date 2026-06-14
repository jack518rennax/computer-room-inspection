package com.patrol.framework.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j / Swagger 配置
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("机房巡检系统 API")
                        .version("1.0.0")
                        .description("机房巡检系统后端接口文档")
                        .contact(new Contact()
                                .name("patrol-team")
                                .email("15809051811@163.com")));
    }
}
