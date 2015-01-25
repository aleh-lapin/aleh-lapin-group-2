package com.epam.jms;

import java.util.Properties;

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

public class RemoteJMSClient implements MessageListener{
	private static final Logger LOG = Logger.getLogger(RemoteJMSClient.class);
	
	private String destinationName = "jms/queue/test";
	private String destinationName2 = "jms/queue/test2";
	 
    private Context ic;
    private ConnectionFactory cf;
    private Connection conn;
	private Session session;
	
    private MessageProducer publisher;
    private MessageConsumer subscriber;
    
	private static RemoteJMSClient instance;
	
	private RemoteJMSClient() {
		try {
			final Properties env = new Properties();
		 
			env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
			env.put(Context.PROVIDER_URL, "remote://localhost:4447");
			env.put(Context.SECURITY_PRINCIPAL, "user1");
			env.put(Context.SECURITY_CREDENTIALS, "password");
			ic = new InitialContext(env);
			
			cf = (ConnectionFactory)ic.lookup("jms/RemoteConnectionFactory");
            Queue queue = (Queue)ic.lookup(destinationName);
 
            conn = cf.createConnection();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            publisher = session.createProducer(queue);
            
            Queue queue2 = (Queue)ic.lookup(destinationName2);
            subscriber = session.createConsumer(queue2);
            subscriber.setMessageListener(this);
 
            conn.start();
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static RemoteJMSClient getInstance() {
		if (instance == null) {
			instance = new RemoteJMSClient();
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
		messageStr = "Console: " + messageStr;
		TextMessage message = session.createTextMessage(messageStr);
        publisher.send(message);
        LOG.info("Send: " + messageStr);

	}

	public void onMessage(Message arg0) {
		try {
			TextMessage message = (TextMessage) arg0;
			String messageStr = message.getText();
			System.out.println(messageStr);
			LOG.info("Received: " + messageStr);
		} catch (JMSException e) {
			LOG.error(e);
		}
	}
	
}
