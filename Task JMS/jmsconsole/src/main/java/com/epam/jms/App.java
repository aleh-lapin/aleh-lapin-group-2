package com.epam.jms;

import java.util.Scanner;

import javax.jms.JMSException;

import org.apache.log4j.Logger;

public class App {
	private static final Logger LOG = Logger.getLogger(App.class);
	
	private static Scanner sc = new Scanner(System.in);
	private final static String EXIT_CODE = "-1";
	
	public static void main(String[] args) {
		RemoteJMSClient client = null;
		try {
			client = RemoteJMSClient.getInstance();
			while (true) {
				System.out.println("Enter message or '-1' to exit:");
				String message = sc.nextLine();
				if (!EXIT_CODE.equals(message)) {
					try {
						client.sendMessage(message);
					} catch (JMSException e) {
						LOG.error(e);
					}
				} else {
					return;
				}
			}
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

}
