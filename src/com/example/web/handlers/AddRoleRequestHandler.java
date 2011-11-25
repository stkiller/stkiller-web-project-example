package com.example.web.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bl.dataaccess.IBLAccessManager;
import com.example.dal.valueobject.RoleVO;
import com.example.web.helper.AvailableActionType;
import com.example.web.helper.BeanUtilsHelper;
import com.example.web.resolution.ForwardResolution;
import com.example.web.resolution.IResolution;
import com.example.web.resolution.RedirectResolution;

public class AddRoleRequestHandler implements IRequestHandler {
	private static final String FORWARD_PATH = "/WEB-INF/view/AddRole.jsp";

	private IBLAccessManager accessManager;
	private BeanUtilsHelper beanHelper;	

	public AddRoleRequestHandler(IBLAccessManager accessManager, BeanUtilsHelper beanHelper) {
		super();
		this.accessManager = accessManager;
		this.beanHelper = beanHelper;
	}

	@Override
	public IResolution parseRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!req.getParameterMap().containsKey("name")) {
			if (req.getParameterMap().containsKey("id")) {
				RoleVO role = accessManager.retrieveRole(new Long(req.getParameter("id")));
				if (role != null) {
					req.setAttribute("role", role);
				}
			}
			return new ForwardResolution(FORWARD_PATH);

		} else {
			RoleVO role = new RoleVO();
			beanHelper.populateBean(role, req.getParameterMap());
			accessManager.writeRole(role);
			return new RedirectResolution("index.html?action=" + AvailableActionType.VIEW);
		}
	}

}
