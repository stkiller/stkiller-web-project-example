package com.example.web.handlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bl.dataaccess.IBLAccessManager;
import com.example.dal.valueobject.GroupVO;
import com.example.dal.valueobject.UserVO;
import com.example.web.helper.AvailableActionType;
import com.example.web.helper.BeanUtilsHelper;
import com.example.web.resolution.ForwardResolution;
import com.example.web.resolution.IResolution;
import com.example.web.resolution.RedirectResolution;

public class AddUserRequestHandler implements IRequestHandler {
	private static final String ADD_USER_JSP = "/WEB-INF/view/AddUser.jsp";

	private IBLAccessManager accessManager;
	private BeanUtilsHelper beanUtilsHelper;	

	public AddUserRequestHandler(IBLAccessManager accessManager, BeanUtilsHelper beanUtilsHelper) {
		super();
		this.accessManager = accessManager;
		this.beanUtilsHelper = beanUtilsHelper;
	}

	@Override
	public IResolution parseRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!req.getParameterMap().containsKey("name")) {
			if(req.getParameterMap().containsKey("id")){
			String userId = req.getParameter("id");
			UserVO user = null;
			if (userId != null) {
				user = accessManager.retrieveUser(new Long(userId));
				req.setAttribute("user", user);
			}}
			List<GroupVO> groups;
			groups = accessManager.retrieveGroups();
			req.setAttribute("groups", groups);
			return new ForwardResolution(ADD_USER_JSP);
		} else {
			UserVO user = new UserVO();
			beanUtilsHelper.populateBean(user, req.getParameterMap());
			user.setGroup(accessManager.retrieveGroup(Long.parseLong(req.getParameter("group_id"))));
			accessManager.writeUser(user);
			return new RedirectResolution("index.html?action="+AvailableActionType.VIEW);
		}
	}

}
