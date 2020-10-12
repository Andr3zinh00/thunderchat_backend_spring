package com.pw.thunderchat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addRedirectViewController("/api/v2/api-docs", "/v2/api-docs");
//		registry.addRedirectViewController("/api/swagger-resources/configuration/ui",
//				"/swagger-resources/configuration/ui");
//		registry.addRedirectViewController("/api/swagger-resources/configuration/security",
//				"/swagger-resources/configuration/security");
//		registry.addRedirectViewController("/api/swagger-resources", "/swagger-resources");
//	}

	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {

	        registry
	                .addResourceHandler("swagger-ui.html")
	                .addResourceLocations("classpath:/META-INF/resources/");

	        registry
	                .addResourceHandler("/webjars/**")
	                .addResourceLocations("classpath:/META-INF/resources/webjars/");
	    }
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("ThunderChat").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.pw.thunderchat.controller")).paths(PathSelectors.any()).build();

	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Thunderchat").description("Documentação da API Thunderchat")
				.license("Thunderchat license").version("1.0").build();

	}
}
