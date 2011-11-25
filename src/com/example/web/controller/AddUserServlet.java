package com.example.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bl.dataaccess.IBLAccessManager;
import com.example.dal.valueobject.GroupVO;
import com.example.dal.valueobject.UserVO;
import com.example.web.helper.BeanUtilsHelper;

//@WebServlet(urlPatterns = { "/addUser.html" })
public class AddUserServlet extends HttpServlet {

	private static final long serialVersionUID = -390150401389923507L;
	private static final String ADD_USER_JSP = "/WEB-INF/view/AddUser.jsp";

	private IBLAccessManager accessManager;
	private BeanUtilsHelper beanUtilsHelper;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		parseRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		parseRequest(req, resp);
	}

	private void parseRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameterMap().size() <= 1) {
			String userId = req.getParameter("id");
			UserVO user = null;
			if (userId != null) {
				user = accessManager.retrieveUser(new Long(userId));
				req.setAttribute("user", user);
			}
			List<GroupVO> groups;
			groups = accessManager.retrieveGroups();
			req.setAttribute("groups", groups);
			RequestDispatcher reqDispatcher = req.getRequestDispatcher(ADD_USER_JSP);
			reqDispatcher.forward(req, resp);
		} else {
			UserVO user = new UserVO();
			beanUtilsHelper.populateBean(user, req.getParameterMap());
			user.setGroup(accessManager.retrieveGroup(Long.parseLong(req.getParameter("group_id"))));
			accessManager.writeUser(user);
			resp.sendRedirect("index.html");
		}
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		accessManager = (IBLAccessManager) getServletContext().getAttribute("accessManager");
		beanUtilsHelper = (BeanUtilsHelper) getServletContext().getAttribute("beanUtils");
	}

}
