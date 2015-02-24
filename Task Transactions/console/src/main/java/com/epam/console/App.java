package com.epam.console;

import com.epam.console.messaging.control.ControlBus;

public class App {
		
	public static void main(String[] args) throws InterruptedException {
		ControlBus controlBus = new ControlBus();
		controlBus.startup();
		Thread.sleep(180000);
	}

}
