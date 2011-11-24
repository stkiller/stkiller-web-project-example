package com.example.dal.valueobject;

import java.util.LinkedList;
import java.util.List;

public class GroupVO extends ValueObject {
	String description;
	List<RoleVO> roles;

	public GroupVO(){
		
	}
	
	public GroupVO(String name, String description) {
		super(name);
		this.description = description;
		roles = new LinkedList<RoleVO>();
	}

	public GroupVO(Long id, String name, String description) {
		super(id, name);
		this.description = description;
		roles = new LinkedList<RoleVO>();
	}

	public void setRoles(List<RoleVO> roles) {
		this.roles = roles;
	}

	public List<RoleVO> getRoles() {
		return roles;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("GroupVO [description=%s", description);
	}

}
