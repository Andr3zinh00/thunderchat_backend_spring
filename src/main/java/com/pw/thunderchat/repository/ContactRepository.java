package com.pw.thunderchat.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pw.thunderchat.model.Contact;

@Repository
public interface ContactRepository extends MongoRepository<Contact, ObjectId> {

}
