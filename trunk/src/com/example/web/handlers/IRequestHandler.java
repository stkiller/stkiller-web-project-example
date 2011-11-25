package com.example.web.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.web.resolution.IResolution;

public interface IRequestHandler {
	public IResolution parseRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
