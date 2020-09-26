/**
 * 
 */
package com.example.testingrestdocs;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Marc on 25 Sep 2020
 */
public class Message {
	
	@NotNull(message = "Sender cannot be null")
	private String sender;
	
	@Pattern(regexp="HI|HELLO",message = "Message can be only 'HI' or 'HELLO'")
	private String content;
	
	public Message() {}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
}
