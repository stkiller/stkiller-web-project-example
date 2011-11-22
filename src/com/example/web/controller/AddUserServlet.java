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
import com.example.dal.exceptions.DBException;
import com.example.dal.valueobject.GroupVO;
import com.example.dal.valueobject.UserVO;

@WebServlet(urlPatterns = { "/addUser.html" })
public class AddUserServlet extends HttpServlet {

	private static final long serialVersionUID = -390150401389923507L;
	private static final String ADD_USER_JSP = "/WEB-INF/view/AddUser.jsp";

	private IAccessManager accessManager;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		parseRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		parseRequest(req, resp);
	}

	private void parseRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameterMap().size() <= 0) {
			List<GroupVO> groups;
			try {
				groups = accessManager.retriveGroups();
				req.setAttribute("groups", groups);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RequestDispatcher reqDispatcher = req.getRequestDispatcher(ADD_USER_JSP);
			reqDispatcher.forward(req, resp);
		} else {
			try {
				UserVO user = new UserVO();
				user.setLogin(req.getParameter("login"));
				user.setPassword(req.getParameter("pass"));
				user.setGroup(accessManager.retrieveGroup(Long.parseLong(req.getParameter("group_id"))));
				accessManager.writeUser(user);
				resp.sendRedirect("index.html");
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		accessManager = (IAccessManager) getServletContext().getAttribute("accessManager");
	}

}
