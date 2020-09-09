package com.pw.thunderchat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pw.thunderchat.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	@Query("{$or:[ {mention:?0}, {email:?1} ]}")
	List<User> findUserByMentionOrEmail(String mention, String email);

	@Query("{mention:?0}")
	User findUserByName(String mention);

	@Query("{$and:[ {password:?1}, {$or:[ {mention:?0}, {email:?0} ] } ] }")
	Optional<User> findUserWithLoginAndPassword(String login, String password);
}
