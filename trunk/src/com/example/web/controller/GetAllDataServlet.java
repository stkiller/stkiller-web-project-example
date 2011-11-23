package com.example.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bl.dataaccess.IAccessManager;
import com.example.dal.valueobject.GroupVO;
import com.example.dal.valueobject.RoleVO;
import com.example.dal.valueobject.UserVO;

@WebServlet(urlPatterns={"/index.html"})
public class GetAllDataServlet extends HttpServlet {
	private static final long serialVersionUID = -3469018018739864179L;
	private static final String GET_USERS_JSP = "/WEB-INF/view/GetAllData.jsp";
	
	private IAccessManager accessManager;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		parseRequest(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		parseRequest(req,resp);
	}

	private void parseRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		List<UserVO> users = accessManager.retrieveUsers();
		req.setAttribute("users", users);
		List<GroupVO> groups = accessManager.retrieveGroups();
		req.setAttribute("groups", groups);
		List<RoleVO> roles = accessManager.retrieveRoles();
		req.setAttribute("roles", roles);
		RequestDispatcher requestDispatcher = req.getRequestDispatcher(GET_USERS_JSP);
		requestDispatcher.forward(req, resp);
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		accessManager = (IAccessManager) getServletContext().getAttribute("accessManager");
	}
	
	
}
