package com.pw.thunderchat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pw.thunderchat.model.User;
import com.pw.thunderchat.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

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
		//MEU DEUS DO CEU, COMO QUE PODE TER QUE USAR UM MAP PRA VER UM JSON
		//PQ O SPRING NÃO DEIXAR TER MULTIPLAS VARIAVEIS NO @RequestBody
		return this.userService.login(json.get("password"), json.get("login"));
	}

	@PostMapping
	public User createUser(@RequestBody @Validated User user) {
		System.out.println(user);
		return userService.create(user);
	}
	

	
	
}
