package com.example.web.handlers;

import java.io.IOException;

import javax.servlet.ServletException;

import com.example.bl.dataaccess.IBLAccessManager;
import com.example.bl.exceptions.DataRetrievalException;
import com.example.bl.exceptions.DataWriteException;
import com.example.bl.exceptions.ValidationException;
import com.example.dal.valueobject.RoleVO;
import com.example.web.entities.execution.IExecutionContext;
import com.example.web.entities.resolution.ForwardResolution;
import com.example.web.entities.resolution.IResolution;
import com.example.web.entities.resolution.RedirectResolution;
import com.example.web.helper.AvailableActionType;
import com.example.web.helper.BeanUtilsHelper;

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
	public IResolution parseRequest(IExecutionContext context) throws ServletException, IOException {

		if (!context.isParameterPresent("name")) {
			try {
				if (context.isParameterPresent("id")) {
					RoleVO role = accessManager.retrieveRole(new Long(context.getParameter("id")));
					if (role != null) {
						context.setAttribute("role", role);
					}
				}
				return new ForwardResolution(FORWARD_PATH);
			} catch (DataRetrievalException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			try {
				RoleVO role = new RoleVO();
				beanHelper.populateBean(role, context.getParameterMap());
				accessManager.writeRole(role);
				return new RedirectResolution("index.html?action=" + AvailableActionType.VIEW);
			} catch (ValidationException ex) {
				context.addValidationError(ex.getValidationResult().getValidationResultMessage());
				return new ForwardResolution(FORWARD_PATH);
			} catch (DataWriteException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
