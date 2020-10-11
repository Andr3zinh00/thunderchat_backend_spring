package com.pw.thunderchat.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.pw.thunderchat.service.impl.UserDetailImpl;
import com.pw.thunderchat.utils.JWTFilter;

/**
 * @author André Classe de configurações de segurança do Spring security
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailImpl userDetail;

	/**
	 * Configura qual é a classe a ser usada na autenticação de requisições, neste
	 * caso usara o service criado do UserDetail
	 * 
	 * @param builder
	 * @throws Exception
	 */
	@Autowired
	protected void configAuthBuilder(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetail);
	}

	@Autowired
	private JWTFilter jwtFilter;

	/**
	 * Bean para usar a classe pai do authentication manager nos #Autowired na
	 * autorização JWT
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * Bean para encriptar senhas na autenticação
	 * 
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources",
				"/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		http.cors().and().authorizeRequests().anyRequest().authenticated().and().httpBasic();
		http.cors().and().authorizeRequests().antMatchers("/user/create", "/user/login", "/swagger-ui.html").permitAll()
				.anyRequest().authenticated().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		http.csrf().disable();
	}

	/**
	 * Mais CORS...
	 * 
	 * @return
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
		// setAllowCredentials(true) is important, otherwise:
		// The value of the 'Access-Control-Allow-Origin' header in the response must
		// not be the wildcard '*' when the request's credentials mode is 'include'.
		configuration.setAllowCredentials(true);
		// setAllowedHeaders is important! Without it, OPTIONS preflight request
		// will fail with 403 Invalid CORS request
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
