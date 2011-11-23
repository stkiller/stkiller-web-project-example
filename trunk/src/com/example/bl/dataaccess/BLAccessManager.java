package com.example.bl.dataaccess;

import java.util.List;

import com.example.dal.dataaccess.AccessManager;
import com.example.dal.dataaccess.IAccessManager;
import com.example.dal.exceptions.NoSuchFactoryException;
import com.example.dal.factories.DAOFactoryType;
import com.example.dal.valueobject.GroupVO;
import com.example.dal.valueobject.RoleVO;
import com.example.dal.valueobject.UserVO;

public class BLAccessManager implements IBLAccessManager {
	
	private IAccessManager accessManager;
	
	public BLAccessManager(DAOFactoryType dbType, String dbUser, String dbPass, String dbURL){
		try {
			accessManager = new AccessManager(dbType,dbUser,dbPass,dbURL);
		} catch (NoSuchFactoryException e) {
			throw new RuntimeException(e);
		}
	}

	public List<UserVO> retrieveUsers() {
		return accessManager.retrieveUsers();
	}

	public List<UserVO> retrieveUsersWithGroups() {
		return accessManager.retrieveUsersWithGroups();
	}

	public UserVO retrieveUser(Long id) {
		if(id==null || id <0 ){
			throw new RuntimeException("User's id cannot be null or negative !");
		}
		return accessManager.retrieveUser(id);
	}

	public UserVO retrieveUserWithGroup(Long id) {
		if(id==null || id <0 ){
			throw new RuntimeException("User's id cannot be null or negative !");
		}
		return accessManager.retrieveUserWithGroup(id);
	}

	public long writeUser(UserVO userVO) {
		if(userVO==null){
			throw new RuntimeException("User object cannot be null!");
		}
		return accessManager.writeUser(userVO);
	}

	public boolean removeUser(UserVO user) {
		if(user==null){
			throw new RuntimeException("User object cannot be null!");
		}
		return accessManager.removeUser(user);
	}

	public List<GroupVO> retrieveGroups() {
		return accessManager.retrieveGroups();
	}

	public GroupVO retrieveGroup(Long id) {
		if(id==null || id <0 ){
			throw new RuntimeException("Group's id cannot be null or negative !");
		}
		return accessManager.retrieveGroup(id);
	}

	public long writeGroup(GroupVO group) {
		if(group==null){
			throw new RuntimeException("Group object cannot be null!");
		}
		return accessManager.writeGroup(group);
	}

	public boolean removeGroup(GroupVO group) {
		if(group==null){
			throw new RuntimeException("Group object cannot be null!");
		}
		return accessManager.removeGroup(group);
	}

	public List<RoleVO> retrieveRoles() {
		return accessManager.retrieveRoles();
	}

	public RoleVO retrieveRole(Long id) {
		if(id==null || id <0 ){
			throw new RuntimeException("Role's id cannot be null or negative !");
		}
		return accessManager.retrieveRole(id);
	}

	public long writeRole(RoleVO role) {
		if(role==null){
			throw new RuntimeException("Role object cannot be null!");
		}
		return accessManager.writeRole(role);
	}

	public boolean removeRole(RoleVO role) {
		if(role==null){
			throw new RuntimeException("Role object cannot be null!");
		}
		return accessManager.removeRole(role);
	}
	
	

}
