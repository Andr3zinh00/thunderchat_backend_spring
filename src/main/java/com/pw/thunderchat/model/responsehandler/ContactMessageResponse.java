package com.pw.thunderchat.model.responsehandler;

import com.pw.thunderchat.model.Messages;
import com.pw.thunderchat.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactMessageResponse {

	private Messages lastMsg;
	private User contact;
	
	
}
