package com.pw.thunderchat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author André
 * Adiciona configurações cors para requisições na API
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	
	/**
	 * Mapeamento do cors 
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowCredentials(true).allowedOrigins("http://localhost:3000")
				.allowedHeaders("Authorization", "Cache-Control", "Content-Type", "Accept", "X-Requested-With",
						"Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Origin")
//				.exposedHeaders("Access-Control-Expose-Headers", "Authorization", "Cache-Control", "Content-Type",
//						"Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Origin")
				.exposedHeaders("Authorization")
				.allowedMethods("GET", "OPTIONS", "POST", "PUT", "DELETE", "PATCH");
	}
}
