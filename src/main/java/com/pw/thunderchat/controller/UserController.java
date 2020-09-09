package com.pw.thunderchat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pw.thunderchat.model.AuthenticationRequest;
import com.pw.thunderchat.model.AuthenticationResponse;
import com.pw.thunderchat.model.User;
import com.pw.thunderchat.service.UserService;
import com.pw.thunderchat.utils.JWTUtils;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JWTUtils jwtUtils;

	@GetMapping(path = "/get-all")
	public List<User> getAll() {
		return this.userService.getAll();
	}

	@GetMapping("/{id}")
	public User getById(@PathVariable String id) {
		return this.userService.getUserById(id);
	}

	@PostMapping("/login")
	public User login(@RequestBody Map<String, String> json) {
		// MEU DEUS DO CEU, COMO QUE PODE TER QUE USAR UM MAP PRA VER UM JSON
		// PQ O SPRING NÃO DEIXAR TER MULTIPLAS VARIAVEIS NO @RequestBody
		return this.userService.login(json.get("password"), json.get("username"));
	}

	@PostMapping("/create")
	public User createUser(@RequestBody User user) {
		System.out.println(user);
		return userService.create(user);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) {
		System.out.println(authRequest);
		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Informações invalidas");
		}
		
		final UserDetails user= userDetailsService.loadUserByUsername(authRequest.getUsername());
		
		final String token = jwtUtils.generateToken(user);
		return ResponseEntity.ok(new AuthenticationResponse(token));

	}

}
