package com.epam;

import java.util.ArrayList;
import java.util.List;

import com.epam.impl.AccountFileStorage;
import com.epam.impl.CurrencyRateStorage;

public class App {

	private static CurrencyGeneratorThread currencyGeneratorThread;
	
	public static void main(String[] args) {
		
		CurrencyRateStorage currencyRateStorage = new CurrencyRateStorage();
		AccountFileStorage accountFileStorage = new AccountFileStorage(currencyRateStorage);
		
		currencyGeneratorThread = new CurrencyGeneratorThread("Generator", currencyRateStorage);
		currencyGeneratorThread.start();
		
		List<ClientThread> clientThreads = new ArrayList<ClientThread>();
		for (int i = 0; i < 10; i++) {
			ClientThread clientThread = new ClientThread(i, "Client_" + i, accountFileStorage);
			clientThread.start();
			clientThreads.add(clientThread);
		}
		
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		currencyGeneratorThread.interrupt();
		for (ClientThread clientThread : clientThreads) {
			clientThread.interrupt();
		}
	}

}
