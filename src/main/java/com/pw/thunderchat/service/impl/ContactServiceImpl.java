package com.pw.thunderchat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.pw.thunderchat.errorhandler.InvalidOperationException;
import com.pw.thunderchat.errorhandler.NotFoundException;
import com.pw.thunderchat.model.Contact;
import com.pw.thunderchat.model.User;
import com.pw.thunderchat.repository.ContactRepository;
import com.pw.thunderchat.repository.UserRepository;
import com.pw.thunderchat.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public String addContact(String contactId, String userId) {

		User user = this.userRepository.findById(contactId)
				.orElseThrow(() -> new NotFoundException("Usuário inexistente!"));

		Contact contact = new Contact();
		contact.setUserId(userId);

		Example<Contact> exemp = Example.of(contact);

		// caso seja o primeiro contato a ser adicionado, não haverá nenhum doc com o
		// userId
		// e a busca será nula
		// e com o orElseGet ele recebe um novo Contact para ser adicionado no DB
		Contact con = this.contactRepository.findOne(exemp).orElseGet(() -> new Contact());

		// caso o usuario já exista na lista de contatos
		if (con.getContactsList().contains(user))
			throw new InvalidOperationException("Usuário já existe em sua lista de contatos!");

		if (con.getContactsList() == null) {
			List<User> list = new ArrayList<User>();

			con.setContactsList(list);
			list.add(user);
			con.setUserId(userId);

		} else
			con.getContactsList().add(user);

		System.out.println(con);
		this.contactRepository.save(con);

		return "Contato adicionado com sucesso!";
	}

	@Override
	public Contact getContacts(String id) {
		Contact c = new Contact();
		c.setUserId(id);

		Example<Contact> ex = Example.of(c);
		Optional<Contact> con = this.contactRepository.findOne(ex);

		// se o usuário existe no DB e a lista de contatos dele não está vazia
		if (con.isPresent() && con.get().getContactsList().size() != 0)
			return con.get();

		throw new NotFoundException("O Usuário não possui contatos!");
	}

}
