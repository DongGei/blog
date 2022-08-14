package com.donggei.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)          // 是否使用swagger
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.donggei.controller"))
                .build();
    }
    private ApiInfo apiInfo() {
        Contact dongGei = new Contact("DongGei", "www.donggei.top", "2645981073@qq.com");
        return new ApiInfoBuilder()
                .title("swagger API文档")
                .contact(dongGei)
                .description("测试接口文档")
                .version("1.0")
                .build();
    }
}
