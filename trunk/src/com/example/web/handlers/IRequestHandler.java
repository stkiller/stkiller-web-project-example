package com.example.web.handlers;

import java.io.IOException;

import javax.servlet.ServletException;

import com.example.web.entities.execution.IExecutionContext;
import com.example.web.entities.resolution.IResolution;

public interface IRequestHandler {
	public IResolution parseRequest(IExecutionContext context) throws ServletException, IOException;
}
