package com.example.dal.factories.postgre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.example.dal.dao.IManyToManyDAO;
import com.example.dal.valueobject.RoleVO;

public class PostgreRoleDAO implements IManyToManyDAO<RoleVO> {
	private static final String SELECT_ALL = "select * from roles";
	private static final String SELECT = "select * from roles where id=%1$d";
	private static final String UPDATE = "update roles set name='%2$s',description ='%3$s' where id=%1$d";
	private static final String DELETE = "delete from roles where id= %1$d";
	private static final String INSERT = "insert into roles(id, name,description) values (%1$d,'%2$s','%3$s')";
	private static final String GET_GROUPS = "select group_id from groupsroles where role_id=%1$d";
	private static final String ADD_GROUP = "insert into groupsroles(group_id, role_id) values (%1$d,%2$d)";
	private static final String REMOVE_GROUP = "delete from groupsroles where group_id=%1$d and role_id=%2$d";

	@Override
	public List<Long> getDependentsIDs(RoleVO owner, Connection connection) throws SQLException {
		List<Long> result = new LinkedList<Long>();
		Statement statement = connection.createStatement();
		ResultSet resSet = statement.executeQuery(String.format(GET_GROUPS, owner.getId()));
		while (resSet.next()) {
			result.add(resSet.getLong("group_id"));
		}
		return result;
	}

	@Override
	public boolean removeDependent(RoleVO owner, Long dependentID, Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		int result = statement.executeUpdate(String.format(REMOVE_GROUP, dependentID, owner.getId()));
		return 0 < result ? true : false;
	}

	@Override
	public boolean addDependent(RoleVO owner, Long dependentID, Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		int result = statement.executeUpdate(String.format(ADD_GROUP, dependentID, owner.getId()));
		return 0 < result ? true : false;
	}

	@Override
	public List<RoleVO> retrieve(Connection connection) throws SQLException {
		List<RoleVO> result = new LinkedList<RoleVO>();
		Statement statement = connection.createStatement();
		ResultSet resSet = statement.executeQuery(SELECT_ALL);
		while (resSet.next()) {
			RoleVO current = new RoleVO(resSet.getLong("id"), resSet.getString("name"), resSet.getString("description"));
			result.add(current);
		}
		return result;
	}

	@Override
	public RoleVO retrieve(long id, Connection connection) throws SQLException {
		RoleVO result = null;
		Statement statement = connection.createStatement();
		ResultSet resSet = statement.executeQuery(String.format(SELECT, id));
		if (resSet.next()) {
			result = new RoleVO(resSet.getLong("id"), resSet.getString("name"), resSet.getString("description"));
		}
		return result;
	}

	@Override
	public boolean update(RoleVO item, Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		int result = statement
				.executeUpdate(String.format(UPDATE, item.getId(), item.getName(), item.getDescription()));
		return 0 < result ? true : false;
	}

	@Override
	public boolean delete(RoleVO item, Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		int result = statement.executeUpdate(String.format(DELETE, item.getId()));
		return 0 < result ? true : false;
	}

	@Override
	public long insert(RoleVO item, Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate(String.format(INSERT, item.getId(), item.getName(), item.getDescription()));
		return item.getId();
	}

}