package com.qc.mongo.batch.model;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="USER")
public class User {

	
	private String name;
	
	public User() {
		super();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "User [ name=" + name + "]";
	}
	
	
}
