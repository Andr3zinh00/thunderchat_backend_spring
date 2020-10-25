
package com.pw.thunderchat.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pw.thunderchat.model.Contact;
import com.pw.thunderchat.model.User;
import com.pw.thunderchat.service.ContactService;

import io.swagger.annotations.ApiOperation;

//
@RestController
@RequestMapping("/contact")
public class ContactController {

	@Autowired
	ContactService contactService;

	@PostMapping("/add")
	@ApiOperation("Este método emite uma mensagem (websocket) na rota (/queue/sendback), caso a operação (addContact)"
			+ " seja concluida com sucesso, esta mensagem deve ser capturada no frontend para fazer "
			+ "a adição do contato em tempo real.")
	public Map<String, String> addContact(@RequestBody Map<String, String> json) {
		return Collections.singletonMap("message",
				this.contactService.addContact(json.get("mention"), json.get("userId")));
	}

	@GetMapping("/{id}")
	@Deprecated
	@ApiOperation("Use a rota /contact-chat, que pega todos os contatos + a ultima msg trocada entre eles")
	public Map<String, List<User>> getContactsById(@PathVariable(value = "id") String id) {
		return Collections.singletonMap("contacts", this.contactService.getContacts(id));
	}

	@DeleteMapping("/{userOne}/{userTwo}")
	@ApiOperation("Não existe diferença na ordem entre os id(s) informados")
	public Contact deleteContact(@PathVariable("userOne") String wantTodel, @PathVariable("userTwo") String toDel) {

		return this.contactService.delContact(wantTodel, toDel);
	}

}
