package com.example.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bl.dataaccess.IBLAccessManager;
import com.example.dal.valueobject.GroupVO;
import com.example.dal.valueobject.RoleVO;
import com.example.web.helper.BeanUtilsHelper;

@WebServlet(urlPatterns = { "/addGroup.html" })
public class AddGroupServlet extends HttpServlet {
	private static final long serialVersionUID = -668656867873917796L;
	private static final String ADD_GROUP_JSP = "/WEB-INF/view/AddGroup.jsp";

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
			if (req.getParameterMap().containsKey("id")) {
				String id = req.getParameter("id");
				GroupVO group = accessManager.retrieveGroup(new Long(id));
				req.setAttribute("group", group);
			}
			List<RoleVO> roles;
			roles = accessManager.retrieveRoles();
			req.setAttribute("roles", roles);
			RequestDispatcher reqDispatcher = req.getRequestDispatcher(ADD_GROUP_JSP);
			reqDispatcher.forward(req, resp);
		} else {
			try {
				GroupVO group = new GroupVO();
				beanUtilsHelper.populateBean(group, req.getParameterMap());
				List<RoleVO> roles = new ArrayList<RoleVO>();
				if (req.getParameterMap().containsKey("roles_id")) {
					for (String roleID : req.getParameterValues("roles_id")) {
						if (roleID == null) {
							continue;
						}
						roles.add(accessManager.retrieveRole(Long.parseLong(roleID)));
					}
				}
				group.setRoles(roles);
				accessManager.writeGroup(group);
				resp.sendRedirect("index.html");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
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
