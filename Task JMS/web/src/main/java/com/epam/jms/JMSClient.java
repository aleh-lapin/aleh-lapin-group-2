package com.epam.jms;

import java.util.LinkedList;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

public class JMSClient implements MessageListener{
	private static final Logger LOG = Logger.getLogger(JMSClient.class);
	
	private String destinationName = "queue/test";
	private String destinationName2 = "queue/test2";
	 
    private Context ic;
    private ConnectionFactory cf;
    private Connection conn;
	private Session session;
	
    private MessageProducer publisher;
    private MessageConsumer subscriber;
    
	private static JMSClient instance;
	
	private LinkedList<String> messages = new LinkedList<String>();
	
	private JMSClient() {
		try {
			ic = new InitialContext();
			cf = (ConnectionFactory) ic.lookup("/ConnectionFactory");
			
			Queue queue = (Queue)ic.lookup(destinationName2);
			 
            conn = cf.createConnection();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            publisher = session.createProducer(queue);
            
            Queue queue2 = (Queue)ic.lookup(destinationName);
            
            subscriber = session.createConsumer(queue2);
            subscriber.setMessageListener(this);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static JMSClient getInstance() {
		if (instance == null) {
			instance = new JMSClient();
		}
		return instance;
	}
	
	public void close() {
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
			LOG.error(e);
		}
	}
	
	public void sendMessage(String messageStr) throws JMSException {
		messageStr = "Web: " + messageStr;
		TextMessage message = session.createTextMessage(messageStr);
        publisher.send(message);
        LOG.info("Send: " + messageStr);

	}
	
	public String getMessage() {
		synchronized (messages) {
			if (messages.size() == 0) {
				try {
					messages.wait(20000);
				} catch (InterruptedException e) {
				}
			}
			String message = messages.pollFirst();
			if (message == null) {
				message = "null";
			}
			return message;
		}
	}

	public void onMessage(Message arg0) {
		synchronized (messages) {
			TextMessage message = (TextMessage) arg0;
			try {
				String messageStr = message.getText();
				messages.add(messageStr);
				messages.notifyAll();
				LOG.info("Received: " + messageStr);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
