package com.example.web.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bl.dataaccess.IBLAccessManager;
import com.example.dal.valueobject.GroupVO;
import com.example.dal.valueobject.RoleVO;
import com.example.dal.valueobject.UserVO;
import com.example.web.helper.AvailableActionType;
import com.example.web.resolution.IResolution;
import com.example.web.resolution.RedirectResolution;

public class DeleteEntityRequestHandler implements IRequestHandler {

	private IBLAccessManager accessManager;

	public DeleteEntityRequestHandler(IBLAccessManager accessManager) {
		super();
		this.accessManager = accessManager;
	}

	@Override
	public IResolution parseRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String objectType = req.getParameter("type");
		String id = req.getParameter("id");
		if (objectType != null && id != null) {
			if (objectType.equals("user")) {
				UserVO user = new UserVO();
				user.setId(new Long(id));
				accessManager.removeUser(user);
			}
			if (objectType.equals("role")) {
				RoleVO role = new RoleVO();
				role.setId(new Long(id));
				accessManager.removeRole(role);
			}
			if (objectType.equals("group")) {
				GroupVO group = new GroupVO();
				group.setId(new Long(id));
				accessManager.removeGroup(group);
			}
		}
		return new RedirectResolution("index.html?action=" + AvailableActionType.VIEW);
	}

}
