package com.example.web.entities.execution;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExecutionContext implements IExecutionContext{
	HttpServletRequest request;
	HttpServletResponse response;
	
	public ExecutionContext(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}

	@Override
	public void addValidationError(String message) {
		request.setAttribute("validation", message);
	}

	@Override
	public HttpServletRequest getRequest() {
		return request;
	}

	@Override
	public HttpServletResponse getResponse() {
		return response;
	}

	@Override
	public boolean isParameterPresent(String name) {
		return request.getParameterMap().containsKey(name);
	}

	@Override
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public String getParameter(String name) {
		return request.getParameter(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return request.getParameterMap();
	}

	@Override
	public String[] getParameterValues(String arg0) {
		return request.getParameterValues(arg0);
	}

	@Override
	public void setAttribute(String arg0, Object arg1) {
		request.setAttribute(arg0, arg1);
	}		
	
	
}
