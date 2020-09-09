package com.pw.thunderchat.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author André
 * Ainda estou decidindo como modelar as notificações...
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "contacts")
public class Notification {
	
	private String _id;
	
	private String userId;
	
	private List<Messages> notificationContet; 
}
