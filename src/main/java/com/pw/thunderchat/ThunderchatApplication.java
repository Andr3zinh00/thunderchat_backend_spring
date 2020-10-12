package com.pw.thunderchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author André
 * @since 02/09/2020
 *
 */
@SpringBootApplication
@ComponentScan({ "com.pw.thunderchat.service", "com.pw.thunderchat.controller", "com.pw.thunderchat.config",
		"com.pw.thunderchat.utils","com.pw.thunderchat.errorhandler" })
public class ThunderchatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThunderchatApplication.class, args);
	}

}
