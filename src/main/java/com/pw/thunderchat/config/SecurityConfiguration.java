package com.pw.thunderchat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pw.thunderchat.service.impl.UserDetailImpl;
import com.pw.thunderchat.utils.JWTFilter;

/**
 * @author André
 * Classe de configurações de segurança do Spring security
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailImpl userDetail;

	/**
	 * Configura qual é a classe a ser usada na autenticação de requisições,
	 * neste caso usará o service criado do UserDetail 
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
	 * Bean para usar a classe pai do authentication manager nos #Autowired na autorização JWT
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


	/**
	 * Bean para encriptar senhas na autenticação
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * configura os tipos de requisições aceitas
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		http.cors().and().authorizeRequests().anyRequest().authenticated().and().httpBasic();
		http.cors().and().authorizeRequests().antMatchers("/user/authenticate", "/user/create").permitAll().anyRequest().authenticated()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		http.csrf().disable();
	}

	
}
