package com.epam.servlet;

import java.io.IOException;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/main")
@RolesAllowed(value = { "user_role" })
public class MainController extends AbstractServlet {
	private static final long serialVersionUID = 1L;
       
    public MainController() {
        super();
    }

	@Override
	void doPostGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
	}

}
