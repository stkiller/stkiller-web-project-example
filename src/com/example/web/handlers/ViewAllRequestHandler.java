package com.example.web.handlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bl.dataaccess.IBLAccessManager;
import com.example.dal.valueobject.GroupVO;
import com.example.dal.valueobject.RoleVO;
import com.example.dal.valueobject.UserVO;
import com.example.web.resolution.ForwardResolution;
import com.example.web.resolution.IResolution;

public class ViewAllRequestHandler implements IRequestHandler {
	private static final String VIEW = "/WEB-INF/view/GetAllData.jsp";

	private IBLAccessManager accessManager;	

	public ViewAllRequestHandler(IBLAccessManager accessManager) {
		super();
		this.accessManager = accessManager;
	}

	@Override
	public IResolution parseRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<UserVO> users = accessManager.retrieveUsersWithGroups();
		req.setAttribute("users", users);
		List<GroupVO> groups = accessManager.retrieveGroups();
		req.setAttribute("groups", groups);
		List<RoleVO> roles = accessManager.retrieveRoles();
		req.setAttribute("roles", roles);
		return new ForwardResolution(VIEW);
	}

}
