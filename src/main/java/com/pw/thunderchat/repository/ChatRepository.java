package com.pw.thunderchat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pw.thunderchat.model.Chat;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

	@Query("{ $or: [ {memberOne:?0, memberTwo:?1}, {memberOne:?1, memberTwo:?0} ] }")
	Optional<Chat> findChatByMember(String memberOneId, String memberTwoId);

	@DeleteQuery("{ $or: [ {memberOne:?0, memberTwo:?1}, {memberOne:?1, memberTwo:?0} ] }")
	Optional<Chat> deleteByUsersId(String memberOneId, String memberTwoId);

	@Query("{$or:[{memberOne:?0}, {memberTwo:?0}]}")
	Optional<List<Chat>> findAllChatsByUserId(String userId);
}
