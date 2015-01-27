package com.epam.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
        propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(
        propertyName = "destination", propertyValue = "queue/test") })
public class JmsMDB implements MessageListener {

	@Override
	public void onMessage(Message arg0) {
		try {
			TextMessage tm = (TextMessage) arg0;
	        String text = tm.getText();
	        MessageStorage.pugMessage(text);
	        System.out.println(text);
		} catch (JMSException e) {
            e.printStackTrace();
        }
	}

}
