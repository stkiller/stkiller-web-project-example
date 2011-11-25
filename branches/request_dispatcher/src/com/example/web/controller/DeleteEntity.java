package com.example.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bl.dataaccess.IBLAccessManager;
import com.example.dal.valueobject.GroupVO;
import com.example.dal.valueobject.RoleVO;
import com.example.dal.valueobject.UserVO;

//@WebServlet(urlPatterns={"/deleteEntity.html"})
public class DeleteEntity extends HttpServlet {
	private static final long serialVersionUID = -8120180638048816501L;
	private IBLAccessManager accessManager;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		parseRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		parseRequest(req, resp);
	}

	private void parseRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String objectType = req.getParameter("type");
		String id = req.getParameter("id");
		if(objectType!=null && id != null){
			if(objectType.equals("user")){
				UserVO user = new UserVO();
				user.setId(new Long(id));
				accessManager.removeUser(user);
			}
			if(objectType.equals("role")){
				RoleVO role = new RoleVO();
				role.setId(new Long(id));
				accessManager.removeRole(role);
			}
			if(objectType.equals("group")){
				GroupVO group = new GroupVO();
				group.setId(new Long(id));
				accessManager.removeGroup(group);
			}
			resp.sendRedirect("index.html");
		}
	}

	@Override
	public void init() throws ServletException {
		super.init();
		accessManager = (IBLAccessManager) getServletContext().getAttribute("accessManager");
	}
	
	
}
