package com.example.web.helper;

public enum AvailableActionType {
	VIEW("view"),
	ADD_USER("add_user"),
	ADD_GROUP("add_group"),
	DELETE_ENTITY("delete_entity");
	
	private final String name;
	
	private AvailableActionType(String name){
		this.name= name;
	}

	public String getName() {
		return name;
	}
}
