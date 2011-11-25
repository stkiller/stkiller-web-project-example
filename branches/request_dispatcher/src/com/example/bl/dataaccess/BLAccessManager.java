package com.example.bl.dataaccess;

import java.util.List;

import org.apache.log4j.Logger;

import com.example.dal.dataaccess.AccessManager;
import com.example.dal.dataaccess.IAccessManager;
import com.example.dal.exceptions.NoSuchFactoryException;
import com.example.dal.factories.DAOFactoryType;
import com.example.dal.valueobject.GroupVO;
import com.example.dal.valueobject.RoleVO;
import com.example.dal.valueobject.UserVO;

public class BLAccessManager implements IBLAccessManager {

	private IAccessManager accessManager;

	public BLAccessManager(DAOFactoryType dbType, String dbUser, String dbPass, String dbURL) {
		try {
			accessManager = new AccessManager(dbType, dbUser, dbPass, dbURL);
		} catch (NoSuchFactoryException e) {
			throw new RuntimeException(e);
		}
	}

	public List<UserVO> retrieveUsers() {
		try {
			return accessManager.retrieveUsers();
		} catch (RuntimeException ex) {
			Logger.getLogger(getClass()).error("Error on retrieveUsers : ",ex);
			throw ex;
		}
	}

	public List<UserVO> retrieveUsersWithGroups() {
		try {
			return accessManager.retrieveUsersWithGroups();
		} catch (RuntimeException ex) {
			Logger.getLogger(getClass()).error("Error on retrieveUsersWithGroups : ",ex);
			throw ex;
		}
	}

	public UserVO retrieveUser(Long id) {
		try {
			if (id == null || id < 0) {
				throw new RuntimeException("User's id cannot be null or negative !");
			}
			return accessManager.retrieveUser(id);
		} catch (RuntimeException ex) {
			Logger.getLogger(getClass()).error("Error on retrieveUser : ",ex);
			throw ex;
		}
	}

	public UserVO retrieveUserWithGroup(Long id) {
		try {
			if (id == null || id < 0) {
				throw new RuntimeException("User's id cannot be null or negative !");
			}
			return accessManager.retrieveUserWithGroup(id);
		} catch (RuntimeException ex) {
			Logger.getLogger(getClass()).error("Error on retrieveUserWithGroup : ",ex);
			throw ex;
		}
	}

	public long writeUser(UserVO userVO) {
		try {
			if (userVO == null) {
				throw new RuntimeException("User object cannot be null!");
			}
			return accessManager.writeUser(userVO);
		} catch (RuntimeException ex) {
			Logger.getLogger(getClass()).error("Error on writeUser : ",ex);
			throw ex;
		}
	}

	public boolean removeUser(UserVO user) {
		try {
			if (user == null) {
				throw new RuntimeException("User object cannot be null!");
			}
			return accessManager.removeUser(user);
		} catch (RuntimeException ex) {
			Logger.getLogger(getClass()).error("Error on removeUser : ",ex);
			throw ex;
		}
	}

	public List<GroupVO> retrieveGroups() {
		try {
			return accessManager.retrieveGroups();
		} catch (RuntimeException ex) {
			Logger.getLogger(getClass()).error("Error on retrieveGroups : ",ex);
			throw ex;
		}
	}

	public GroupVO retrieveGroup(Long id) {
		try {
			if (id == null || id < 0) {
				throw new RuntimeException("Group's id cannot be null or negative !");
			}
			return accessManager.retrieveGroup(id);
		} catch (RuntimeException ex) {
			Logger.getLogger(getClass()).error("Error on retrieveGroup : ",ex);
			throw ex;
		}
	}

	public long writeGroup(GroupVO group) {
		try {
			if (group == null) {
				throw new RuntimeException("Group object cannot be null!");
			}
			return accessManager.writeGroup(group);
		} catch (RuntimeException ex) {
			Logger.getLogger(getClass()).error("Error on writeGroup : ",ex);
			throw ex;
		}
	}

	public boolean removeGroup(GroupVO group) {
		try {
			if (group == null) {
				throw new RuntimeException("Group object cannot be null!");
			}
			return accessManager.removeGroup(group);
		} catch (RuntimeException ex) {
			Logger.getLogger(getClass()).error("Error on removeGroup : ",ex);
			throw ex;
		}
	}

	public List<RoleVO> retrieveRoles() {
		try {
			return accessManager.retrieveRoles();
		} catch (RuntimeException ex) {
			Logger.getLogger(getClass()).error("Error on retrieveRoles : ",ex);
			throw ex;
		}
	}

	public RoleVO retrieveRole(Long id) {
		try {
			if (id == null || id < 0) {
				throw new RuntimeException("Role's id cannot be null or negative !");
			}
			return accessManager.retrieveRole(id);
		} catch (RuntimeException ex) {
			Logger.getLogger(getClass()).error("Error on retrieveRole : " ,ex);
			throw ex;
		}
	}

	public long writeRole(RoleVO role) {
		try {
			if (role == null) {
				throw new RuntimeException("Role object cannot be null!");
			}
			return accessManager.writeRole(role);
		} catch (RuntimeException ex) {
			Logger.getLogger(getClass()).error("Error on writeRole : ",ex);
			throw ex;
		}
	}

	public boolean removeRole(RoleVO role) {
		try {
			if (role == null) {
				throw new RuntimeException("Role object cannot be null!");
			}
			return accessManager.removeRole(role);
		} catch (RuntimeException ex) {
			Logger.getLogger(getClass()).error("Error on removeRole : ",ex);
			throw ex;
		}
	}

}
