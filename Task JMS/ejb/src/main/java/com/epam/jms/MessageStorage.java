package com.epam.jms;

import java.util.LinkedList;

public class MessageStorage {

	private static LinkedList<String> messages = new LinkedList<String>();
	
	public static String getMessage() {
		synchronized (messages) {
		if (messages.size() == 0) {
			try {
				messages.wait(20000);
			} catch (InterruptedException e) {
			}
		}
		String message = messages.poll();
		return message != null ? message : "null";
		
		}
	}
	
	public static void pugMessage(String message) {
		synchronized (messages) {
			messages.add(message);
			messages.notifyAll();
		}
	}
	
}
