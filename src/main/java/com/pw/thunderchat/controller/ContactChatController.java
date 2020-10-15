package com.pw.thunderchat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pw.thunderchat.model.responsehandler.ContactMessageResponse;
import com.pw.thunderchat.model.responsehandler.Response;
import com.pw.thunderchat.service.impl.ContactChatImpl;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/contact-chat")
public class ContactChatController {

	@Autowired
	private ContactChatImpl contactChatImpl;

	/**
	 * Bem melhor usar este aqui do que o da rota contactController...
	 * 
	 * @param id
	 * @return uma lista de contatos com sua ultima mensagem trocada com o user
	 */
	@ApiOperation("Retona todos os contatos de um usuário + suas ultima mensagem trocada")
	@GetMapping("/{id}")
	public Response<List<ContactMessageResponse>> getContactsWithLastMessage(@PathVariable("id") String id) {
		return this.contactChatImpl.getContactsWithLastMessage(id);
	}
}
