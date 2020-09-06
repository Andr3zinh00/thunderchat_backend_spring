package com.pw.thunderchat.service;

import java.util.List;

import com.pw.thunderchat.model.User;

//
public interface ContactService {
	
	List<User> getContacts(String userId);

	String addContact(String mention, String userId);
}