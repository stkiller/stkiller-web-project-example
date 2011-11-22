/**
 * 
 */
package com.example.bl.dataaccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.example.dal.dao.IBaseDao;
import com.example.dal.dao.IManyToManyDAO;
import com.example.dal.exceptions.DBException;
import com.example.dal.exceptions.NoSuchFactoryException;
import com.example.dal.factories.AbstractDAOFactory;
import com.example.dal.factories.DAOFactoryType;
import com.example.dal.factories.IdentityIncrementor;
import com.example.dal.valueobject.GroupVO;
import com.example.dal.valueobject.RoleVO;
import com.example.dal.valueobject.UserVO;

/**
 * @author stkiller
 * 
 */
public class AccessManager implements IAccessManager {
	private static final int TRANSACTION_ISOLATION = Connection.TRANSACTION_REPEATABLE_READ;

	AbstractDAOFactory abFactory;
	IBaseDao<UserVO> userDAO;
	IManyToManyDAO<GroupVO> groupDAO;
	IManyToManyDAO<RoleVO> roleDAO;

	/**
	 * 
	 * @param dbType
	 *            - the type of DB that should be used by {@link AccessManager}
	 * @param dbUser
	 *            - user name that should be used to access the DB
	 * @param dbPassword
	 *            - user password that should be used to access the DB
	 * @param dbURL
	 *            - DB URL that should be used to access it
	 * @throws NoSuchFactoryException
	 *             if there is provided invalid {@link DAOFactoryType}
	 */
	public AccessManager(DAOFactoryType dbType, String dbUser, String dbPassword, String dbURL)
			throws NoSuchFactoryException {
		abFactory = AbstractDAOFactory.getFactory(dbType, dbUser, dbPassword, dbURL);
		userDAO = abFactory.getUserDAO();
		groupDAO = abFactory.getGroupDAO();
		roleDAO = abFactory.getRoleDAO();
		List<UserVO> users;
		try {
			users = retrieveUsers();
			List<GroupVO> groups = retriveGroups();
			List<RoleVO> roles = retrieveRoles();
			IdentityIncrementor.getIdetities().put(UserVO.class, new Long(users.size()+1));
			IdentityIncrementor.getIdetities().put(GroupVO.class, new Long(groups.size()+1));
			IdentityIncrementor.getIdetities().put(RoleVO.class, new Long(roles.size()+1));
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Used internally, for retrieve a connection with set parameters
	 * 
	 * @return a connection with expected set of parameters
	 * @throws DBException
	 */
	private Connection retrieveConnection() throws DBException {
		try {
			Connection connection = abFactory.getConnection();
			connection.setTransactionIsolation(TRANSACTION_ISOLATION);
			connection.setAutoCommit(false);
			return connection;
		} catch (SQLException ex) {
			throw new DBException(String.format("There is an error retrieving connection: %1$s", ex.getMessage()));
		}
	}

	/**
	 * Uses {@link AbstractDAOFactory} returnConnection method to return the
	 * connection
	 * 
	 * @param connection
	 *            - the connection that should be returned
	 * @throws DBException
	 */
	private void returnConnection(Connection connection) throws DBException {
		abFactory.returnConnection(connection);
	}

	/**
	 * Rollback all modifications done within that connection.
	 * 
	 * @param connection
	 *            - target connection that should be rollbacked
	 * @throws DBException
	 */
	private void rollbackConnection(Connection connection) throws DBException {
		try {
			connection.rollback();
		} catch (SQLException ex) {
			throw new DBException(String.format("SQL commands cannot be rollbacked: %1$s", ex.getMessage()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.bl.dataaccess.IAccessManager#retrieveUsers()
	 */
	@Override
	public List<UserVO> retrieveUsers() throws DBException {
		Connection connection = retrieveConnection();
		try {
			List<UserVO> result = userDAO.retrieve(connection);
			connection.commit();
			return result;
		} catch (SQLException ex) {
			rollbackConnection(connection);
			throw new DBException(String.format("User's retrieving wasn't successful: %1$s", ex.getMessage()));
		} finally {
			returnConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.bl.dataaccess.IAccessManager#retrieveUsersWithGroups()
	 */
	@Override
	public List<UserVO> retrieveUsersWithGroups() throws DBException {
		Connection connection = retrieveConnection();
		try {
			List<UserVO> users = userDAO.retrieve(connection);
			for (UserVO userVO : users) {
				userVO.setGroup(this.retrieveGroup(userVO.getGroupID()));
			}
			connection.commit();
			return users;
		} catch (SQLException ex) {
			rollbackConnection(connection);
			throw new DBException(String.format("User's retrieving wasn't successful: %1$s", ex.getMessage()));
		} finally {
			returnConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.bl.dataaccess.IAccessManager#retrieveUser(long)
	 */
	@Override
	public UserVO retrieveUser(long id) throws DBException {
		Connection connection = retrieveConnection();
		try {
			UserVO result = userDAO.retrieve(id, connection);
			connection.commit();
			return result;
		} catch (SQLException ex) {
			rollbackConnection(connection);
			throw new DBException(String.format("User's retrieving wasn't successful: %1$s", ex.getMessage()));
		} finally {
			returnConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.bl.dataaccess.IAccessManager#retrieveUserWithGroup(long)
	 */
	@Override
	public UserVO retrieveUserWithGroup(long id) throws DBException {
		Connection connection = retrieveConnection();
		try {
			UserVO result = userDAO.retrieve(id, connection);
			result.setGroup(this.retrieveGroup(result.getGroupID()));
			connection.commit();
			return result;
		} catch (SQLException ex) {
			rollbackConnection(connection);
			throw new DBException(String.format("User's retrieving wasn't successful: %1$s", ex.getMessage()));
		} finally {
			returnConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.bl.dataaccess.IAccessManager#writeUser(com.example.dal.
	 * valueobject.UserVO)
	 */
	@Override
	public long writeUser(UserVO userVO) throws DBException {
		if (userVO == null) {
			return -1;
		}
		Connection connection = retrieveConnection();
		try {
			if (userVO.getGroup() != null) {
				this.writeGroup(userVO.getGroup(), connection);
			}
			long result = userDAO.insert(userVO, connection);
			connection.commit();
			return result;
		} catch (SQLException ex) {
			rollbackConnection(connection);
			throw new DBException(String.format("User's writing wasn't successful: %1$s", ex.getMessage()));
		} finally {
			returnConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.bl.dataaccess.IAccessManager#removeUser(com.example.dal.
	 * valueobject.UserVO)
	 */
	@Override
	public boolean removeUser(UserVO user) throws DBException {
		if (user == null) {
			return false;
		}
		Connection connection = retrieveConnection();
		try {
			boolean result = userDAO.delete(user, connection);
			connection.commit();
			return result;
		} catch (SQLException ex) {
			rollbackConnection(connection);
			throw new DBException(String.format("There is an error on removing user : %1$s", ex.getMessage()));
		} finally {
			returnConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.bl.dataaccess.IAccessManager#retriveGroups()
	 */
	@Override
	public List<GroupVO> retriveGroups() throws DBException {
		Connection connection = retrieveConnection();
		try {
			List<GroupVO> result = groupDAO.retrieve(connection);
			for (GroupVO group : result) {
				List<RoleVO> roles = new LinkedList<RoleVO>();
				List<Long> rolesIDs = groupDAO.getDependentsIDs(group, connection);
				for (Long roleID : rolesIDs) {
					RoleVO role = roleDAO.retrieve(roleID, connection);
					roles.add(role);
				}
				group.setRoles(roles);
			}
			connection.commit();
			return result;
		} catch (SQLException ex) {
			rollbackConnection(connection);
			throw new DBException(String.format("Groups' retrieving wasn't successful: %1$s", ex.getMessage()));
		} finally {
			returnConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.bl.dataaccess.IAccessManager#retrieveGroup(long)
	 */
	@Override
	public GroupVO retrieveGroup(long id) throws DBException {
		Connection connection = retrieveConnection();
		try {
			GroupVO result = groupDAO.retrieve(id, connection);
			List<RoleVO> roles = new LinkedList<RoleVO>();
			List<Long> rolesIDs = groupDAO.getDependentsIDs(result, connection);
			for (Long roleID : rolesIDs) {
				RoleVO role = roleDAO.retrieve(roleID, connection);
				roles.add(role);
			}
			result.setRoles(roles);
			connection.commit();
			return result;
		} catch (SQLException ex) {
			rollbackConnection(connection);
			throw new DBException(String.format("Group's retrieving wasn't successful: %1$s", ex.getMessage()));
		} finally {
			returnConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.bl.dataaccess.IAccessManager#writeGroup(com.example.dal.
	 * valueobject.GroupVO)
	 */
	@Override
	public long writeGroup(GroupVO group) throws DBException {
		if (group == null) {
			return -1;
		}
		Connection connection = retrieveConnection();
		try {
			return this.writeGroup(group, connection);
		} finally {
			returnConnection(connection);
		}
	}

	/**
	 * Used internally, shares the same connection with other
	 * operations(writeUser, for example)
	 * 
	 * @param group
	 *            - {@link GroupVO} object that should be written
	 * @param connection
	 *            - {@link Connection} that is used to access the DB
	 * @return the id of written {@link GroupVO} object.
	 * @throws DBException
	 */
	private long writeGroup(GroupVO group, Connection connection) throws DBException {
		try {
			// check if group is already in DB
			if (group.getId() == null) {
				groupDAO.insert(group, connection);
			} else {
				groupDAO.update(group, connection);
			}
			// write group's roles
			List<RoleVO> newRoles = group.getRoles();
			if (newRoles != null) {
				// get group's existing roles
				List<Long> existingRoles = groupDAO.getDependentsIDs(group, connection);
				for (RoleVO role : newRoles) {
					// if current role is already mapped to group
					if (existingRoles.contains(role.getId())) {
						continue;
					}
					// check if that role already exists in DB
					if (roleDAO.retrieve(role.getId(), connection) == null) {
						// if not - write it
						roleDAO.insert(role, connection);
					}
					// map role to group
					groupDAO.addDependent(group, role.getId(), connection);
				}
			}
			connection.commit();
			return group.getId();
		} catch (SQLException ex) {
			rollbackConnection(connection);
			throw new DBException(String.format("Group's retrieving wasn't successful: %1$s", ex.getMessage()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.example.bl.dataaccess.IAccessManager#removeGroup(com.example.dal.
	 * valueobject.GroupVO)
	 */
	@Override
	public boolean removeGroup(GroupVO group) throws DBException {
		if (group == null) {
			return false;
		}
		Connection connection = retrieveConnection();
		List<Long> roles;
		try {
			// remove this group from groupsroles
			roles = groupDAO.getDependentsIDs(group, connection);
			if (roles != null && 0 < roles.size()) {
				for (Long roleID : roles) {
					groupDAO.removeDependent(group, roleID, connection);
				}
			}
			boolean result = groupDAO.delete(group, connection);
			connection.commit();
			return result;
		} catch (SQLException ex) {
			rollbackConnection(connection);
			throw new DBException(String.format("Group's removing wasn't successful: %1$s", ex.getMessage()));
		} finally {
			returnConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.bl.dataaccess.IAccessManager#retrieveRoles()
	 */
	@Override
	public List<RoleVO> retrieveRoles() throws DBException {
		Connection connection = retrieveConnection();
		try {
			List<RoleVO> result = roleDAO.retrieve(connection);
			for (RoleVO role : result) {
				List<GroupVO> groups = new LinkedList<GroupVO>();
				List<Long> groupsIDs = roleDAO.getDependentsIDs(role, connection);
				for (Long groupID : groupsIDs) {
					GroupVO group = groupDAO.retrieve(groupID, connection);
					groups.add(group);
				}
				role.setGroups(groups);
			}
			connection.commit();
			return result;
		} catch (SQLException ex) {
			rollbackConnection(connection);
			throw new DBException(String.format("Roles' retrieving wasn't successful: %1$s", ex.getMessage()));
		} finally {
			returnConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.bl.dataaccess.IAccessManager#retrieveRole(long)
	 */
	@Override
	public RoleVO retrieveRole(long id) throws DBException {
		Connection connection = retrieveConnection();
		try {
			RoleVO result = roleDAO.retrieve(id, connection);
			if (result == null) {
				return result;
			}
			List<GroupVO> groups = new LinkedList<GroupVO>();
			List<Long> groupsIDs = roleDAO.getDependentsIDs(result, connection);
			for (Long groupID : groupsIDs) {
				GroupVO group = groupDAO.retrieve(groupID, connection);
				groups.add(group);
			}
			result.setGroups(groups);
			connection.commit();
			return result;
		} catch (SQLException ex) {
			rollbackConnection(connection);
			throw new DBException(String.format("Role's retrieving wasn't successful: %1$s", ex.getMessage()));
		} finally {
			returnConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.bl.dataaccess.IAccessManager#writeRole(com.example.dal.
	 * valueobject.RoleVO)
	 */
	@Override
	public long writeRole(RoleVO role) throws DBException {
		if (role == null) {
			return -1;
		}
		Connection connection = retrieveConnection();
		try {
			// check if role is already in DB
			if (role.getId() == null) {
				// insert it
				roleDAO.insert(role, connection);
			} else {
				// update it
				roleDAO.update(role, connection);
			}
			// write groups
			List<GroupVO> newGroups = role.getGroups();
			if ((newGroups != null) && (0 < newGroups.size())) {
				List<Long> oldGroups = roleDAO.getDependentsIDs(role, connection);
				for (GroupVO group : newGroups) {
					if (oldGroups.contains(group.getId())) {
						continue;
					}
					// check if that group already exists in DB
					if (group.getId() == null) {
						// if not - insert it
						groupDAO.insert(group, connection);
					} else {
						// if yes - update it
						groupDAO.update(group, connection);
					}
					// map group to role
					roleDAO.addDependent(role, group.getId(), connection);
				}
				// unmap unused connections
				for (Long oldGroupID : oldGroups) {
					boolean found = false;
					for (GroupVO newGroup : newGroups) {
						if (oldGroupID == newGroup.getId()) {
							found = true;
							break;
						}
						if (!found) {
							roleDAO.removeDependent(role, oldGroupID, connection);
						}
					}
				}
			}
			// commit connection
			connection.commit();
			return role.getId();
		} catch (SQLException ex) {
			rollbackConnection(connection);
			throw new DBException(String.format("Role's writing wasn't successful: %1$s", ex.getMessage()));
		} finally {
			returnConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.bl.dataaccess.IAccessManager#removeRole(com.example.dal.
	 * valueobject.RoleVO)
	 */
	@Override
	public boolean removeRole(RoleVO role) throws DBException {
		if (role == null) {
			return false;
		}
		Connection connection = retrieveConnection();
		try {
			// remove role's groups
			List<Long> groups = roleDAO.getDependentsIDs(role, connection);
			if (groups != null && 0 < groups.size()) {
				for (Long group : groups) {
					roleDAO.removeDependent(role, group, connection);
				}
			}
			// remove role
			boolean result = roleDAO.delete(role, connection);
			connection.commit();
			return result;
		} catch (SQLException ex) {
			rollbackConnection(connection);
			rollbackConnection(connection);
			throw new DBException(String.format("Role's removing wasn't successful: %1$s", ex.getMessage()));
		} finally {
			returnConnection(connection);
		}
	}
}
