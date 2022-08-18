/*
package com.example.finalprojectstoreapp.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hospitalManagement"))
                .paths(PathSelectors.any())
                .build()
                .enableUrlTemplating(false)
                .apiInfo(swaggerMetaData());
    }

    private ApiInfo swaggerMetaData() {
        return new ApiInfo(
                "Address book API",
                "Sample API for Hospital management",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("Ihor Liu", "", "iliu@griddynamics"),
                "API license",
                "",
                Collections.emptyList()
        );
    }
}
*/
