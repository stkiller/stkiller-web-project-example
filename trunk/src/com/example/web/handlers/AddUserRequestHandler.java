package com.example.web.handlers;

import com.example.bl.dataaccess.IBLAccessManager;
import com.example.bl.exceptions.DataRetrievalException;
import com.example.bl.exceptions.DataWriteException;
import com.example.bl.exceptions.ValidationException;
import com.example.dal.valueobject.GroupVO;
import com.example.dal.valueobject.UserVO;
import com.example.web.entities.execution.IExecutionContext;
import com.example.web.entities.resolution.ForwardResolution;
import com.example.web.entities.resolution.IResolution;
import com.example.web.entities.resolution.RedirectResolution;
import com.example.web.helper.AvailableActionType;
import com.example.web.helper.BeanUtilsHelper;

import java.util.List;

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
	public IResolution parseRequest(IExecutionContext context){
		if (!context.isParameterPresent("name")) {
			try {
				return parseEditRequest(context);
			} catch (DataRetrievalException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			try {
				return parseAddRequest(context);
			} catch (DataRetrievalException ex) {
				throw new RuntimeException(ex);
			} catch (DataWriteException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	private IResolution parseEditRequest(IExecutionContext context) throws DataRetrievalException {
		UserVO user = null;
		if (context.isParameterPresent("id")) {
			String userId = context.getParameter("id");
			if (userId != null) {
				user = accessManager.retrieveUser(new Long(userId));
			}
		}
		return parseEditRequest(user, context);
	}

	private IResolution parseEditRequest(UserVO user, IExecutionContext context) throws DataRetrievalException {
		context.setAttribute("user", user);
		List<GroupVO> groups;
		groups = accessManager.retrieveGroups();
		context.setAttribute("groups", groups);
		return new ForwardResolution(ADD_USER_JSP);
	}
	
	private IResolution parseAddRequest(IExecutionContext context) throws DataRetrievalException, DataWriteException{
		UserVO user = new UserVO();
		try {
			beanUtilsHelper.populateBean(user, context.getParameterMap());
			user.setGroup(accessManager.retrieveGroup(Long.parseLong(context.getParameter("group_id"))));
			accessManager.writeUser(user);
			return new RedirectResolution("index.html?action=" + AvailableActionType.VIEW);
		} catch (ValidationException ex) {
			context.addValidationError(ex.getValidationResult().getValidationResultMessage());
			return parseEditRequest(user, context);
		}
	}
}
