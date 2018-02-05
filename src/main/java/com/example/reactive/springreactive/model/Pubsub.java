package com.example.reactive.springreactive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pubsub")
public class Pubsub {
	@Id
	String id;
	String type;
	String message;
	
	
	public Pubsub() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Pubsub(String id, String type, String message) {
		super();
		this.id = id;
		this.type = type;
		this.message = message;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return "Pubsub [id=" + id + ", type=" + type + ", message=" + message + "]";
	}
	
	
}
