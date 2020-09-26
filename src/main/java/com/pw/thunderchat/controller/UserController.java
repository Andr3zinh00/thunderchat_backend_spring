package com.pw.thunderchat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.pw.thunderchat.model.AuthenticationRequest;
import com.pw.thunderchat.model.AuthenticationResponse;
import com.pw.thunderchat.model.User;
import com.pw.thunderchat.service.UserService;
import com.pw.thunderchat.service.impl.UserDetailImpl;
import com.pw.thunderchat.utils.JWTUtils;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailImpl userDetailsImpl;

	@Autowired
	private JWTUtils jwtUtils;

	/**
	 * Fiz apenas por formalidade, mas aparentemente, não será utilizado em nenhum
	 * lugar da aplicação...
	 */
	@Deprecated
	@GetMapping(path = "/get-all")
	public List<User> getAll() {
		return this.userService.getAll();
	}

	@GetMapping("/{id}")
	public User getById(@PathVariable String id) {
		return this.userService.getUserById(id);
	}

	@PostMapping("/create")
	public User createUser(@RequestBody User user) {
		return userService.create(user);
	}

	@PostMapping("/login")
	public Map<String, Object> authenticate(@RequestBody AuthenticationRequest authRequest) {

		Map<String, Object> returnedMap = new HashMap<>();

		User userLogin = userService.login(authRequest.getPassword(), authRequest.getUsername());

		//
		final UserDetails user = userDetailsImpl.userDetailWithRole(userLogin.getMention(), userLogin.getPassword());

		System.out.println(user);

		final String token = jwtUtils.generateToken(user);
		returnedMap.put("token", new AuthenticationResponse(token));
		returnedMap.put("user", userLogin);
//		ResponseEntity.ok(new AuthenticationResponse(token));
		return returnedMap;

	}

	@PutMapping("/update")
	public User update(@RequestBody User user) {
		return this.userService.update(user);
	}

}
