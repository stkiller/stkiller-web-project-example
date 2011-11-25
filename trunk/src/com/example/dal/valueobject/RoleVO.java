package com.example.dal.valueobject;


public class RoleVO extends ValueObject {

	private String description;

	public RoleVO(){
		
	}
	
	public RoleVO(String name, String description) {
		super(name);
		this.description = description;
	}

	public RoleVO(Long id, String name, String description) {
		super(id, name);
		this.description = description;
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

	@Override
	public String toString() {
		return "RoleVO [description=" + description + ", id=" + id + ", name=" + name + "]";
	}
}
