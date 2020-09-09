package com.pw.thunderchat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pw.thunderchat.errorhandler.InvalidOperationException;
import com.pw.thunderchat.errorhandler.NotFoundException;
import com.pw.thunderchat.model.Contact;
import com.pw.thunderchat.model.User;
import com.pw.thunderchat.repository.ContactRepository;
import com.pw.thunderchat.repository.UserRepository;
import com.pw.thunderchat.service.UserService;


/**
 * @author André
 * Service do Usuario
 * Implementação do contrato explicito na interface UserService
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRepository;

	/**
	 *Fiz apenas por formalidade, mas aparentemente, não será utilizado em nenhum lugar da aplicação...
	 */
	@Deprecated
	@Override
	public List<User> getAll() {
		
		return this.userRepository.findAll();
	}

	@Override
	public User create(User user) {
		System.out.println(user);

		List<User> users = this.userRepository.findUserByMentionOrEmail(user.getMention(), user.getEmail());

		if (users.size() != 0)
			throw new InvalidOperationException("Email ou @ já existem em nossa base!");

		String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(encodedPassword);

		User nUser = this.userRepository.save(user);

		Contact c = new Contact();
		c.setUserId(nUser.get_id());
		c.setContactsList(new ArrayList<User>());

		this.contactRepository.save(c);

		return nUser;
	}

	@Override
	public void delete(String id) {
		this.userRepository.deleteById(id);
	}

	@Override
	public User getUserById(String id) {
		return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário inexistente"));

	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User login(String password, String login) {
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		User u = this.userRepository.findUserByName(login);
		
		if (u == null)
			throw new NotFoundException(
					"Login fornecido não é compativel com nenhum registro, verfique os dados informados!");
		
		if (bc.matches(password, u.getPassword()))
			return u;

		throw new NotFoundException(
				"Credenciais fornecidas não são compativeis com os registros, verfique os dados informados!");
	}

}
