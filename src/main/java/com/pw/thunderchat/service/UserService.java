package com.pw.thunderchat.service;

import java.util.List;

import com.pw.thunderchat.model.User;

public interface UserService {

	List<User> getAll();

	User getUserByEmail(String email);

	User getUserById(String id);

	User create(User user);

	void delete(String id);
	
	User login(String password, String login);


}
