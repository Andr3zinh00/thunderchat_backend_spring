package com.pw.thunderchat.service;

import java.util.List;

import com.pw.thunderchat.model.User;

/**
 * @author André
 * Contrato do service de contato
 */
public interface ContactService {
	
	List<User> getContacts(String userId);

	String addContact(String mention, String userId);
}