package com.example.web.handlers;

import java.io.IOException;

import javax.servlet.ServletException;

import com.example.web.entities.execution.IExecutionContext;
import com.example.web.entities.resolution.ForwardResolution;
import com.example.web.entities.resolution.IResolution;

public class LoginErrorHandler implements IRequestHandler {
	private static final String PATH="WEB-INF/view/ErrorLogin.jsp";
	
	
	@Override
	public IResolution parseRequest(IExecutionContext context) throws ServletException, IOException {
		return new ForwardResolution(PATH);
	}

}
