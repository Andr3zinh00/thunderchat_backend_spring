package com.pw.thunderchat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pw.thunderchat.model.User;

@Service
public interface UserService {

	List<User> getAll();

	User getUserByEmail(String email);

	User getUserById(String id);

	User create(User user);

	void delete(String id);
	
	User login(String password, String login);


}
