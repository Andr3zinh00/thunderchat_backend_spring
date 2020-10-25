package com.pw.thunderchat.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pw.thunderchat.model.Notification;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

	@Query("{ user:?0 }")
	Optional<Notification> getByUserId(String userId);

	@Query("{ $and:[ {_id: ?1 } ,{ notificationContent:{ $elemMatch:{_id: ?0 } }} ] }")
	Optional<Notification> getNotificationByMessageIdAndNotificationId(String messageId, String notificationId);
}
