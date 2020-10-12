package com.pw.thunderchat.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.pw.thunderchat.errorhandler.BadRequestException;
import com.pw.thunderchat.errorhandler.InvalidOperationException;
import com.pw.thunderchat.errorhandler.NotFoundException;
import com.pw.thunderchat.model.Contact;
import com.pw.thunderchat.model.EMessageType;
import com.pw.thunderchat.model.Messages;
import com.pw.thunderchat.model.User;
import com.pw.thunderchat.repository.ContactRepository;
import com.pw.thunderchat.repository.UserRepository;
import com.pw.thunderchat.service.ChatService;
import com.pw.thunderchat.service.ContactService;

/**
 * @author André Service de contato Implementação do contrato explicito na
 *         interface ContactService
 */
@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ChatService chatService;

	@Autowired
	SimpMessagingTemplate simpMessageTemplate;

	/**
	 * KKKKKKKKKKKKKK QUE MERDA FOI ESSA Q EU FIZ NEM CONSIGO MAIS ENTENDER O QUE
	 * ISSO ESTA FAZENDO DIREITO
	 */
	@Override
	public String addContact(String mention, String userId) {

		List<User> users = this.userRepository.findUserByMentionOrEmail(mention, null);

		if (users.isEmpty())
			throw new NotFoundException("Mention não encontrada!");

		User isGoingToBeAdded = users.get(0);

		Contact wantsToAdd = getContactByUserId(userId);

		List<User> listWant = wantsToAdd.getContactsList();
		if (listWant.contains(isGoingToBeAdded))
			throw new InvalidOperationException("O usuário já está na lista de contatos!");

		listWant.add(isGoingToBeAdded);
		Contact alreadyAdded = getContactByUserId(isGoingToBeAdded.get_id());

		User user = this.userRepository.findById(wantsToAdd.getUserId())
				.orElseThrow(() -> new NotFoundException("Usuário inexistente"));

		alreadyAdded.getContactsList().add(user);

		this.contactRepository.saveAll(Arrays.asList(alreadyAdded, wantsToAdd));

		// quem mandou o pedido é o memberOne do chat
		this.chatService.create(user, isGoingToBeAdded);

		Messages msg = new Messages(user.getMention() + " aceitou seu pedido e agora está na sua lista de contatos :D",
				"SYSTEM", isGoingToBeAdded.getMention(), EMessageType.INVITE_ACCEPTED, new Date());

		simpMessageTemplate.convertAndSendToUser(isGoingToBeAdded.getMention(), "/queue/sendback", msg);

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
				.orElseThrow(() -> new NotFoundException("Usuário inexistente!"));

	}

	@Override
	public Contact delContact(String wantToDel, String toDel) {
		if (wantToDel.equals(toDel))
			throw new InvalidOperationException("Você está tentando se deletar da sua lista de contatos? (￢_￢)");

		Contact contact = this.contactRepository.findContactByUserId(wantToDel)
				.orElseThrow(() -> new NotFoundException("Não foi possível encontrar os contatos do id: " + wantToDel));

		System.out.println(contact + " contact");
		System.out.println(toDel);
		if (contact.getContactsList().removeIf(user -> toDel.equals(user.get_id()))) {
			this.chatService.delete(wantToDel, toDel);
			System.out.println(contact + " contact");
			return this.contactRepository.save(contact);
		}
		throw new BadRequestException("Verifique as credenciais informadas!");
	}

}
