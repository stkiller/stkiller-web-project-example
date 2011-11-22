package com.example.web.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.example.bl.dataaccess.AccessManager;
import com.example.bl.dataaccess.IAccessManager;
import com.example.dal.exceptions.NoSuchFactoryException;
import com.example.dal.factories.DAOFactoryType;

@WebListener
public class InitializationListener implements ServletContextListener{
	private static final String DB_USER="dbUser";
	private static final String DB_PASS="dbPass";
	private static final String DB_URL="dbURL";
	private static final String DB_TYPE="dbType";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		String dbUser=contextEvent.getServletContext().getInitParameter(DB_USER);
		String dbPass = contextEvent.getServletContext().getInitParameter(DB_PASS);
		String dbURL = contextEvent.getServletContext().getInitParameter(DB_URL);
		String dbType = contextEvent.getServletContext().getInitParameter(DB_TYPE);
		IAccessManager accessManager = null;
		try {
			accessManager = new AccessManager(DAOFactoryType.valueOf(dbType),dbUser,dbPass,dbURL);
		} catch (NoSuchFactoryException e) {
			// TODO implement logging here !
			e.printStackTrace();
		}
		contextEvent.getServletContext().setAttribute("accessManager", accessManager);
	}

}
