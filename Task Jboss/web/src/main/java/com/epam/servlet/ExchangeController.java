package com.epam.servlet;

import java.io.IOException;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.ifaces.IAccount;

@WebServlet("/exchange")
@RolesAllowed(value = { "user_role" })
public class ExchangeController extends AbstractServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject
	IAccount iAccount;
	
    public ExchangeController() {
        super();
    }

	@Override
	void doPostGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("accountId");
		String to = request.getParameter("currencyTo");
		iAccount.changeCurrency(Long.parseLong(id), Long.parseLong(to));
		getServletContext().getRequestDispatcher("/manager.do").forward(request, response);
	}

}
