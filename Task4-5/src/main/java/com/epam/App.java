package com.epam;

import java.util.ArrayList;
import java.util.List;

import com.epam.ifaces.IAccount;
import com.epam.ifaces.ICurrencyExchanger;
import com.epam.ifaces.ICurrencyRate;
import com.epam.ifaces.IStorage;
import com.epam.impl.AccountFileStorage;
import com.epam.impl.AccountServiceImpl;
import com.epam.impl.CurrencyRateServiceImpl;
import com.epam.impl.CurrencyRateStorage;
import com.epam.model.Account;
import com.epam.model.CurrencyRate;

public class App {

	private static CurrencyGeneratorThread currencyGeneratorThread;
	
	public static void main(String[] args) {
		
		IStorage<CurrencyRate, Long> currencyRateStorage = new CurrencyRateStorage();
		IStorage<Account, Long> accountFileStorage = new AccountFileStorage();
		
		ICurrencyRate iCurrencyRate = new CurrencyRateServiceImpl(currencyRateStorage);
		ICurrencyExchanger iCurrencyExchanger = (CurrencyRateServiceImpl)iCurrencyRate;
		
		IAccount iAccount = new AccountServiceImpl(accountFileStorage, iCurrencyExchanger);
		
		currencyGeneratorThread = new CurrencyGeneratorThread("Generator", iCurrencyRate);
		currencyGeneratorThread.start();
		
		List<ClientThread> clientThreads = new ArrayList<ClientThread>();
		for (int i = 0; i < 10; i++) {
			ClientThread clientThread = new ClientThread(i, "Client_" + i, iAccount);
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
