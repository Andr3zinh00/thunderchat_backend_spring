package com.pw.thunderchat.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pw.thunderchat.errorhandler.InvalidOperationException;
import com.pw.thunderchat.errorhandler.NotFoundException;
import com.pw.thunderchat.model.Contact;
import com.pw.thunderchat.model.User;
import com.pw.thunderchat.repository.ContactRepository;
import com.pw.thunderchat.repository.UserRepository;
import com.pw.thunderchat.service.ContactService;

/**
 * @author André
 * Service de contato
 * Implementação do contrato explicito na interface ContactService
 */
@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public String addContact(String mention, String userId) {

		List<User> users = this.userRepository.findUserByMentionOrEmail(mention, null);

		if (users.isEmpty())
			throw new NotFoundException("Mention não encontrada!");

		User isGoingToBeAdded = users.get(0);

		Contact wantsToAdd = getContactByUserId(userId);

		if (wantsToAdd.getContactsList().contains(isGoingToBeAdded))
			throw new InvalidOperationException("O usuário já está na lista de contatos!");

		wantsToAdd.getContactsList().add(isGoingToBeAdded);
		Contact alreadyAdded = getContactByUserId(isGoingToBeAdded.get_id());

		User user = this.userRepository.findById(wantsToAdd.getUserId())
				.orElseThrow(() -> new NotFoundException("Usuário inexistente"));

		alreadyAdded.getContactsList().add(user);

		this.contactRepository.saveAll(Arrays.asList(alreadyAdded, wantsToAdd));

		return "Contato adicionado com sucesso!";
	}

	@Override
	public List<User> getContacts(String userId) {

		Contact con = getContactByUserId(userId);

		if (con.getContactsList().size() == 0)
			throw new NotFoundException("O usuário não possui contatos!");

		return con.getContactsList();
	}

	public Contact getContactByUserId(String userId) {
		return this.contactRepository.findContactByUserId(userId)
				.orElseThrow(() -> new NotFoundException("Usuário inexistente"));
	}

}
