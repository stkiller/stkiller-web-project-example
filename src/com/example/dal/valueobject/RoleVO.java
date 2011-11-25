package com.example.dal.valueobject;

import java.util.LinkedList;
import java.util.List;

public class RoleVO extends ValueObject {

	private String description;
	private List<GroupVO> groups;

	public RoleVO(){
		
	}
	
	public RoleVO(String name, String description) {
		super(name);
		this.description = description;
		groups = new LinkedList<GroupVO>();
	}

	public RoleVO(Long id, String name, String description) {
		super(id, name);
		this.description = description;
		groups = new LinkedList<GroupVO>();
	}

	public void setGroups(List<GroupVO> groups) {
		this.groups = groups;
	}

	public List<GroupVO> getGroups() {
		return groups;
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
		return String.format("RoleVO [description=%s]", description)+super.toString();
	}

}
