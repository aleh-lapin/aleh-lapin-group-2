package com.epam.servlet;

import javax.annotation.security.RolesAllowed;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/pageNotFound")
@RolesAllowed(value = { "user_role" })
public class NotFoundController extends AbstractServlet {
	private static final long serialVersionUID = 1L;
       
    public NotFoundController() {
        super();
    }


	@Override
	void doPostGet(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	}

}
