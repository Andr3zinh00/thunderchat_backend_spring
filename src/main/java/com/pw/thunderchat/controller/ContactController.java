
package com.pw.thunderchat.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pw.thunderchat.model.User;
import com.pw.thunderchat.service.ContactService;

//
@RestController
@RequestMapping("/contact")
public class ContactController {

	@Autowired
	ContactService contactService;

	@PutMapping
	public String addContact(@RequestBody Map<String, String> json) {
		return this.contactService.addContact(json.get("mention"), json.get("userId"));
	}

	@GetMapping("/{id}")
	public Map<String, List<User>> getContacts(@PathVariable(value = "id") String id) {
		return Collections.singletonMap("contacts", this.contactService.getContacts(id));
	}

}
//
//
//	
//	@PutMapping
//	public String addContact(@RequestBody Map<String, String> json) {
//		return contactService.addContact(json.get("contactId"), json.get("userId"));
//	}
//	
//	@GetMapping("/{id}")
//	public Contact getContacts(@PathVariable String id) {
//		return this.contactService.getContacts(id);
//	}
//}
