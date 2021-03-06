package com.wangxin.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by echelon on 2018/07/17.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    //createRestApi() 方法指定扫描的包会生成文档,默认是显示所有接口,可以用@ApiIgnore注解标识该接口不显示
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wangxin.springboot.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Invest RESTful APIs")
                .description("Invest的API文档")
                .termsOfServiceUrl("http://blog.didispace.com/")
                .version("1.0")
                .build();
    }

}
