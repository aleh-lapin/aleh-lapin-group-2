package com.epam.servlet;

import java.io.IOException;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.exceptions.ServiceException;
import com.epam.service.OrderService;

/**
 * Servlet implementation class ExchangeController
 */
@WebServlet("/order")
@RolesAllowed(value = { "user_role" })
public class OrderController extends AbstractServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject
	OrderService orderService;
	
    /**
     * @see AbstractServlet#AbstractServlet()
     */
    public OrderController() {
        super();
    }

	@Override
	void doPostGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ticketId = request.getParameter("ticketId");
		String userId = "1";
		try {
			orderService.order(ticketId, userId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		getServletContext().getRequestDispatcher("/manager.do").forward(request, response);
	}

}
