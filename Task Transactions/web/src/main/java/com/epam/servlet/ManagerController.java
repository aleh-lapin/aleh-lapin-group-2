package com.epam.servlet;

import java.io.IOException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.beans.Ticket;
import com.epam.exceptions.ServiceException;
import com.epam.service.OrderService;


/**
 * Servlet implementation class ManagerController
 */
@WebServlet("/manager.do")
@RolesAllowed(value = { "user_role" })
public class ManagerController extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	Logger LOG = Logger.getLogger(ManagerController.class);
	
	@Inject
	OrderService orderService;
	
	/**
     * @see AbstractServlet#AbstractServlet()
     */
    public ManagerController() {
        super();
    }
    
    @Override
	public void init() throws ServletException {
    	orderService.init();
    	
    	LOG.info("Serv init");
    }



	@Override
	void doPostGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long balance = 0;
		List<Ticket> tickets = null;
		try {
			balance = orderService.getBalance("1");
			LOG.info("Acount loaded");
			tickets = orderService.getTickets();
			LOG.info(tickets);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		request.setAttribute("balance", balance);
		request.setAttribute("tickets", tickets);
		getServletContext().getRequestDispatcher("/manager.jsp").forward(request, response);
		
	}

}
