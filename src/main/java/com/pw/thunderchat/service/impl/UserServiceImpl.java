package com.pw.thunderchat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pw.thunderchat.errorhandler.NotFoundException;
import com.pw.thunderchat.model.User;
import com.pw.thunderchat.repository.UserRepository;
import com.pw.thunderchat.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAll() {
		return this.userRepository.findAll();
	}

	@Override
	public User create(User user) {
		List<User> users = this.userRepository.findUserByMentionOrEmail(user.getMention(), user.getEmail());

		System.out.println(users);

		if (users.size() != 0)
			throw new IllegalArgumentException("Email ou @ já existem em nossa base!");

		return this.userRepository.save(user);
	}

	@Override
	public void delete(String id) {
		this.userRepository.deleteById(id);
	}

	@Override
	public User getUserById(String id) {
		return this.userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário inexistente"));

	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User login(String password, String login) {
		return this.userRepository.findUserWithLoginAndPassword(login, password)
				.orElseThrow(() -> new NotFoundException(
						"Credenciais fornecidas não são compativeis com os registros, verfique os dados informados!"));

	}

}
