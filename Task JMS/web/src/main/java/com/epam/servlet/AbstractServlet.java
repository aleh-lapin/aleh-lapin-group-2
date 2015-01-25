package com.epam.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class AbstractServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AbstractServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPostGet(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPostGet(request, response);
	}
	
	abstract void doPostGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
