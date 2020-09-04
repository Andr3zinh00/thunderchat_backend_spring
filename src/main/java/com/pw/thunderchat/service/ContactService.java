package com.pw.thunderchat.service;

import com.pw.thunderchat.model.Contact;

public interface ContactService {
	String addContact(String contactId, String userId);
	Contact getContacts(String id); 
}
