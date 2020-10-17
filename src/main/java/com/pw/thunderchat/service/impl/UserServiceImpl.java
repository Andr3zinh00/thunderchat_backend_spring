package com.pw.thunderchat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pw.thunderchat.errorhandler.BadRequestException;
import com.pw.thunderchat.errorhandler.InvalidOperationException;
import com.pw.thunderchat.errorhandler.NotFoundException;
import com.pw.thunderchat.model.Chat;
import com.pw.thunderchat.model.Contact;
import com.pw.thunderchat.model.Messages;
import com.pw.thunderchat.model.Notification;
import com.pw.thunderchat.model.User;
import com.pw.thunderchat.repository.ChatRepository;
import com.pw.thunderchat.repository.ContactRepository;
import com.pw.thunderchat.repository.NotificationRepository;
import com.pw.thunderchat.repository.UserRepository;
import com.pw.thunderchat.service.UserService;

/**
 * Service do Usuario Implementação do contrato explicito na interface
 * UserService
 * 
 * @author André
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private ChatRepository chatRepository;

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
		c.setUser(nUser);
		c.setContactsList(new ArrayList<User>());

		Notification notif = new Notification(true, nUser, new ArrayList<Messages>());

		this.contactRepository.save(c);
		this.notificationRepository.save(notif);

		return nUser;
	}

	@Override
	public User delete(String id) {
		// deleta os relacionamentos do usuário... o mongo nao tem on cascade ;-;
		this.notificationRepository.delete(this.notificationRepository.getByUserId(id)
				.orElseThrow(() -> new NotFoundException(("Usuário inexistente!"))));

		this.contactRepository.delete(this.contactRepository.findContactByUserId(id)
				.orElseThrow(() -> new NotFoundException(("Usuário inexistente!"))));

		Optional<List<Chat>> optChats = this.chatRepository.findAllChatsByUserId(id);
		if (optChats.isPresent())
			this.chatRepository.deleteAll(optChats.get());

		return this.userRepository.deleteByIdAndGet(id)
				.orElseThrow(() -> new NotFoundException("Usuário inexistente!"));
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

	@Override
	public User update(User user) {
		System.out.println(user + "   userFromRequest");
		List<User> possibleUsers = this.userRepository.findUserByMentionOrEmail(user.getMention(), user.getEmail());

		if (possibleUsers.size() > 1)
			throw new InvalidOperationException("A @ ou email escolhidos já estão em uso!");

		User retrievedUser = possibleUsers.get(0);

		parseUser(retrievedUser, user);

		return this.userRepository.save(retrievedUser);
	}

	public void parseUser(User userFromDb, User userFromRequest) {
		System.out.println(userFromDb + "   " + userFromRequest);
		//verifica se o usuário mandou a senha na requisição e se ela é menor que 6
		if (userFromRequest.getPassword() != null && userFromRequest.getPassword().trim().length() < 6)
			throw new BadRequestException("A senha deve conter no minimo 6 caracteres!");

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// faz a mesma comparação aqui pra saber se o usuário quer ou não mudar a senha
		if (userFromRequest.getPassword() != null
				&& !encoder.matches(userFromRequest.getPassword(), userFromDb.getPassword())) {
			String encodedPassword = encoder.encode(userFromRequest.getPassword());
			userFromDb.setPassword(encodedPassword);
		}

		userFromDb.setEmail(userFromRequest.getEmail());
		userFromDb.setBirth_date(userFromRequest.getBirth_date());
		userFromDb.setMention(userFromRequest.getMention());
		userFromDb.setName(userFromRequest.getName());
	}

}
