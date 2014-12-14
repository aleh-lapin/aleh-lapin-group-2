package com.epam.servlet;

import java.io.IOException;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet("/login")
@RolesAllowed(value = { "user_role" })
public class LoginServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;
    
	Logger LOG = Logger.getLogger(LoginServlet.class);
	
    public LoginServlet() {
        super();
    }

	@Override
	void doPostGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String psw = request.getParameter("psw");
		LOG.info(user);
		LOG.info(psw);
		request.getRequestDispatcher("./manager.do").forward(request, response);
	}

}
