package com.epam;

import org.apache.log4j.Logger;

import com.epam.ifaces.IAccount;
import com.epam.model.Account;
import com.epam.utils.RandomHelper;

public class ClientThread extends Thread {
	private static final Logger LOG = Logger.getLogger(ClientThread.class);
	
	private long id;
	private IAccount iAccount;

	public ClientThread(long id, String name, IAccount iAccount) {
		super(name);
		this.id = id;
		this.iAccount = iAccount;
	}

	@Override
	public void run() {
		initClient();
		try {
			while (!isInterrupted()) {
				changeCurrencyCount();

				Thread.sleep(100);

				changeCurrency();
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			LOG.info("ClientThread " + getName() + " is stopped");
		}
	}

	private void changeCurrencyCount() {
		Account account = iAccount.findAccont(id);
		iAccount.changeCurrencyCount(id, RandomHelper.getRandomCurrencyCount(account.getCurrentCount(), 10));
	}

	private void changeCurrency() {
		Account account = iAccount.findAccont(id);
		long newCurrencyId = RandomHelper.getRandomCurrency(account.getCurrency());
		iAccount.changeCurrency(id, newCurrencyId);
	}

	private void initClient() {
		Account account = new Account(id, getName(),
				RandomHelper.getRandomCurrency(),
				RandomHelper.getRandomCurrencyCount(1000, 100));
		iAccount.addOrUpdateAccount(account);
	}

}
