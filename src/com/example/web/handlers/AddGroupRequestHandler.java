package com.example.web.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bl.dataaccess.IBLAccessManager;
import com.example.dal.valueobject.GroupVO;
import com.example.dal.valueobject.RoleVO;
import com.example.web.helper.AvailableActionType;
import com.example.web.helper.BeanUtilsHelper;
import com.example.web.resolution.ForwardResolution;
import com.example.web.resolution.IResolution;
import com.example.web.resolution.RedirectResolution;

public class AddGroupRequestHandler implements IRequestHandler {
	private static final String VIEW = "/WEB-INF/view/AddGroup.jsp";
	private IBLAccessManager accessManager;
	private BeanUtilsHelper beanUtilsHelper;

	public AddGroupRequestHandler(IBLAccessManager accessManager, BeanUtilsHelper beanUtilsHelper) {
		super();
		this.accessManager = accessManager;
		this.beanUtilsHelper = beanUtilsHelper;
	}

	@Override
	public IResolution parseRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!req.getParameterMap().containsKey("name")) {
			if (req.getParameterMap().containsKey("id")) {
				String id = req.getParameter("id");
				GroupVO group = accessManager.retrieveGroup(new Long(id));
				req.setAttribute("group", group);
			}
			List<RoleVO> roles;
			roles = accessManager.retrieveRoles();
			req.setAttribute("roles", roles);
			return new ForwardResolution(VIEW);
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
				return new RedirectResolution("index.html?action=" + AvailableActionType.VIEW);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}
