package com.example.demo;


public enum ERole {
	USER("User"),
	ADMIN("Admin");
	
	private final String value;
	
	private ERole(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
