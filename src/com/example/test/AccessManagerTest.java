package com.example.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.dal.exceptions.DBException;
import com.example.dal.exceptions.NoSuchFactoryException;
import com.example.dal.factories.DAOFactoryType;
import com.example.dal.valueobject.GroupVO;
import com.example.dal.valueobject.RoleVO;
import com.example.dal.valueobject.UserVO;
import com.example.service.AccessManager;

public class AccessManagerTest {
	static AccessManager accessManager;
	static UserVO refUser;
	static UserVO refUserWithGroup;
	static GroupVO refGroup;
	static GroupVO refGroupWithRole;
	static RoleVO refRole;
	static RoleVO refRoleWithGroup;

	@BeforeClass
	public static void before() {
		refRole = new RoleVO(0, "FirstRole", "FirstRole description");

		refGroup = new GroupVO(0, "FirstGroup", "FirstGroup description");

		refRoleWithGroup = new RoleVO(refRole.getId(), refRole.getName(), refRole.getDescription());

		refRoleWithGroup.getGroups().add(refGroup);

		refGroupWithRole = new GroupVO(refGroup.getId(), refGroup.getName(), refGroup.getDescription());

		refGroupWithRole.getRoles().add(refRole);

		refUser = new UserVO(0, "FirstUser", "FirstUserLogin", "UserPassword", refGroup.getId());

		refUserWithGroup = new UserVO(refUser.getId(), refUser.getName(), refUser.getLogin(), refUser.getPassword(),
				refUser.getGroupID());

		refUserWithGroup.setGroup(refGroupWithRole);
	}

	@Test
	public void testAccessManager() {
		try {
			accessManager = new AccessManager(DAOFactoryType.MS_SQL, "test", "test",
					"jdbc:jtds:sqlserver://localhost:1433/WebTest");
		} catch (NoSuchFactoryException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testWriteRole() {
		try {
			long result;
			result = accessManager.writeRole(refRole);
			Assert.assertTrue(result == refRole.getId());
		} catch (DBException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testWriteGroup() {
		try {
			long result;
			result = accessManager.writeGroup(refGroupWithRole);
			Assert.assertTrue(result == refGroupWithRole.getId());
		} catch (DBException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testWriteUser() {
		try {
			long result;
			result = accessManager.writeUser(refUser);
			Assert.assertTrue(result == refUser.getId());
		} catch (DBException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testRetrieveUsers() {
		try {
			List<UserVO> users = accessManager.retrieveUsers();
			Assert.assertNotNull(users);
			Assert.assertTrue("There are no users in the list", 0 < users.size());
			Assert.assertEquals(refUser, users.get(0));
			Assert.assertEquals(null, users.get(0).getGroup());
		} catch (DBException ex) {
			Assert.fail(ex.getMessage());
		}
	}

	@Test
	public void testRetrieveUsersWithGroups() {
		try {
			List<UserVO> users = accessManager.retrieveUsersWithGroups();
			Assert.assertNotNull(users);
			Assert.assertTrue("There are no users in the list", 0 < users.size());
			Assert.assertEquals(refUserWithGroup, users.get(0));
			Assert.assertEquals(refGroupWithRole, users.get(0).getGroup());
		} catch (DBException ex) {
			Assert.fail(ex.getMessage());
		}
	}

	@Test
	public void testRetrieveUser() {
		try {
			UserVO actual = accessManager.retrieveUser(refUser.getId());
			Assert.assertEquals(refUser, actual);
			Assert.assertEquals(null, actual.getGroup());
		} catch (DBException ex) {
			Assert.fail(ex.getMessage());
		}
	}

	@Test
	public void testRetrieveUserWithGroup() {
		try {
			UserVO actual = accessManager.retrieveUserWithGroup(refUser.getId());
			Assert.assertEquals(refUserWithGroup, actual);
			Assert.assertEquals(refGroupWithRole, actual.getGroup());
		} catch (DBException ex) {
			Assert.fail(ex.getMessage());
		}
	}

	@Test
	public void testRetriveGroups() {
		try {
			List<GroupVO> groups = accessManager.retriveGroups();
			Assert.assertNotNull(groups);
			Assert.assertTrue(0 < groups.size());
			Assert.assertEquals(refGroupWithRole, groups.get(0));
		} catch (DBException ex) {
			Assert.fail(ex.getMessage());
		}
	}

	@Test
	public void testRetrieveGroup() {
		try {
			GroupVO group = accessManager.retrieveGroup(refGroup.getId());
			Assert.assertNotNull(group);
			Assert.assertEquals(refGroupWithRole, group);
		} catch (DBException ex) {
			Assert.fail(ex.getMessage());
		}
	}

	@Test
	public void testRetrieveRoles() {
		try {
			List<RoleVO> roles = accessManager.retrieveRoles();
			Assert.assertNotNull(roles);
			Assert.assertTrue(0 < roles.size());
			Assert.assertEquals(refRoleWithGroup, roles.get(0));
		} catch (DBException ex) {
			Assert.fail(ex.getMessage());
		}
	}

	@Test
	public void testRetrieveRole() {
		try {
			RoleVO role = accessManager.retrieveRole(refRole.getId());
			Assert.assertNotNull(role);
			Assert.assertEquals(refRoleWithGroup, role);
		} catch (DBException ex) {
			Assert.fail(ex.getMessage());
		}
	}

	@AfterClass
	public static void after() {
		try {
			boolean result = accessManager.removeUser(refUser);
			Assert.assertTrue(result);
			result = accessManager.removeGroup(refGroup);
			Assert.assertTrue(result);
			result = accessManager.removeRole(refRole);
			Assert.assertTrue(result);
		} catch (DBException ex) {
			Assert.fail(ex.getMessage());
		}
	}
}
