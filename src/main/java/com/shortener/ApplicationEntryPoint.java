package com.shortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ApplicationEntryPoint {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationEntryPoint.class, args);
    }

    @Bean
    public Docket phoneBookApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("link shortener service")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.regex("/v1.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Simple Phone book rest webservice.")
                .description("based on spring boot, lombok, spring data, ... technologies.")
                .contact("m.dehghan")
                .version("1.0.0")
                .build();
    }

}
