package com.pw.thunderchat.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pw.thunderchat.model.Contact;

@Repository
public interface ContactRepository extends MongoRepository<Contact, String> {

	
	@Query("{userId:?0}")
	 Optional<Contact> findContactByUserId(String userId);
}
