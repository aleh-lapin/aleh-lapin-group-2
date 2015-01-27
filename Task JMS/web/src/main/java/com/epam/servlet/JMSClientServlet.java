package com.epam.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.security.RolesAllowed;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.jms.MessageStorage;


@WebServlet("/JMSClientServlet")
@RolesAllowed(value = { "user_role" })
public class JMSClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String destinationName2 = "queue/test2";
	
	private Context ic;
    private ConnectionFactory cf;
    private Connection conn;
	private Session session;
	
	private MessageProducer publisher;
	
	@Override
	public void init() throws ServletException {
		try {
			ic = new InitialContext();
			cf = (ConnectionFactory) ic.lookup("/ConnectionFactory");
			
			Queue queue = (Queue)ic.lookup(destinationName2);
			 
            conn = cf.createConnection();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            publisher = session.createProducer(queue);
     	} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
		super.init();
	}

	@Override
	public void destroy() {
		try {
			if(ic != null)
	        {
				ic.close();
	        }
			if (conn != null)
            {
                conn.close();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.destroy();
	}

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter out = response.getWriter();
			out.print(MessageStorage.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String messageStr = "Web: " + request.getParameter("message");
			TextMessage message = session.createTextMessage(messageStr);
	        publisher.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
