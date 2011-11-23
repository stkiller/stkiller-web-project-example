package com.example.web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.example.bl.dataaccess.BLAccessManager;
import com.example.bl.dataaccess.IBLAccessManager;
import com.example.dal.factories.DAOFactoryType;
import com.example.web.helper.BeanUtilsHelper;

@WebListener
public class InitializationListener implements ServletContextListener {
	private static final String DB_USER = "dbUser";
	private static final String DB_PASS = "dbPass";
	private static final String DB_URL = "dbURL";
	private static final String DB_TYPE = "dbType";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		String dbUser = contextEvent.getServletContext().getInitParameter(DB_USER);
		String dbPass = contextEvent.getServletContext().getInitParameter(DB_PASS);
		String dbURL = contextEvent.getServletContext().getInitParameter(DB_URL);
		String dbType = contextEvent.getServletContext().getInitParameter(DB_TYPE);
		IBLAccessManager accessManager = new BLAccessManager(DAOFactoryType.valueOf(dbType), dbUser, dbPass, dbURL);
		contextEvent.getServletContext().setAttribute("accessManager", accessManager);
		contextEvent.getServletContext().setAttribute("beanUtils", new BeanUtilsHelper());
	}

}
