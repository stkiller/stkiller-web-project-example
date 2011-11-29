package com.example.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.web.entities.execution.ExecutionContext;
import com.example.web.entities.resolution.IResolution;
import com.example.web.handlers.IRequestHandler;
import com.example.web.helper.AvailableActionType;

@WebServlet(urlPatterns = {"/index.html"})
public class MyRequestDispatcher extends HttpServlet {
	private static final long serialVersionUID = 456065448089861136L;
	Map<AvailableActionType, IRequestHandler> handlers;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		parseRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		parseRequest(req, resp);
	}

	private void parseRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameterMap().containsKey("action")) {
			String actionName = req.getParameter("action");
			AvailableActionType action = AvailableActionType.valueOf(actionName.toUpperCase());
			if (action != null) {
				invokeHandler(action, req, resp);
			} else {
				throw new RuntimeException("Sorry, undefined action");
			}
		} else {
			invokeHandler(AvailableActionType.VIEW, req, resp);
		}
	}

	private void invokeHandler(AvailableActionType action, HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		IRequestHandler handler = handlers.get(action);
		if (handler != null) {
			IResolution resolution = handler.parseRequest(new ExecutionContext(req, resp));
			resolution.execute(req, resp);
		} else {
			throw new RuntimeException("Sorry, no handler available for this action");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		super.init();
		handlers = (Map<AvailableActionType, IRequestHandler>) getServletContext().getAttribute("handlers");
	}

}
