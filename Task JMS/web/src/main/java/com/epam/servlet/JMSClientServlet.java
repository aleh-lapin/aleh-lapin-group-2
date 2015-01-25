package com.epam.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.security.RolesAllowed;
import javax.jms.JMSException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.jms.JMSClient;

@WebServlet("/JMSClientServlet")
@RolesAllowed(value = { "user_role" })
public class JMSClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private JMSClient jmsClient;

	public void init() throws ServletException {
		jmsClient = JMSClient.getInstance();
	}

	@Override
	public void destroy() {
		jmsClient.close();
		super.destroy();
	}



	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter out = response.getWriter();
			out.print(jmsClient.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String messageStr = request.getParameter("message");
			jmsClient.sendMessage(messageStr);
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }

}
