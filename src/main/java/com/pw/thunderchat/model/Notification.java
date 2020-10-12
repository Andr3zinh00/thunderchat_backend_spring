package com.pw.thunderchat.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author André
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification {

	public Notification(boolean checked, User user, ArrayList<Messages> notificationContent) {
		this.checked = checked;
		this.user = user;
		this.notificationContent = notificationContent;
	}

	@Id
	private String _id;

	@DBRef
	private User user;

	private boolean checked;

	private List<Messages> notificationContent;
}
