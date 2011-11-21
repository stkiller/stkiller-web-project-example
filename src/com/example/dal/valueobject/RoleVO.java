package com.example.dal.valueobject;

import java.util.LinkedList;
import java.util.List;

public class RoleVO extends ValueObject {

	private String description;
	private List<GroupVO> groups;

	public RoleVO(long id, String name, String description) {
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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof RoleVO)) {
			return false;
		}
		RoleVO other = (RoleVO) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (groups == null) {
			if (other.groups != null) {
				return false;
			}
		} else if (!groups.equals(other.groups)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("RoleVO [description=%s, groups=%s]", description, groups);
	}

}
