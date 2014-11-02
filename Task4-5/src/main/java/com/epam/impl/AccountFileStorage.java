package com.epam.impl;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.epam.ifaces.IAccount;
import com.epam.ifaces.ICurrencyExchanger;
import com.epam.model.Account;
import com.epam.model.Currency;

public class AccountFileStorage extends AbstractFileStorage<Account, Long>
		implements IAccount {

	private static final Logger LOG = Logger
			.getLogger(AccountFileStorage.class);

	private String filePath;
	private ICurrencyExchanger iCurrencyExchanger;

	public AccountFileStorage(ICurrencyExchanger iCurrencyExchanger) {
		File file = new File("account.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		this.filePath = file.getAbsolutePath();
		this.iCurrencyExchanger = iCurrencyExchanger;
	}

	@Override
	protected String getFilePath() {
		return filePath;
	}

	public synchronized Account findAccont(long id) {
		return find(id);

	}

	public synchronized void addOrUpdateAccount(Account account) {
		addOrUpdate(account.getId(), account);
	}

	public synchronized void changeCurrencyCount(long id, double currencyCount) {
		Account account = find(id);
		double oldCurrencyCount = account.getCurrentCount();
		account.setCurrentCount(currencyCount);
		addOrUpdateAccount(account);
		LOG.info("Currency count changed: currencyCount  " + oldCurrencyCount
				+ " -> " + account.getCurrentCount());
	}

	public synchronized void changeCurrency(long id, long toCurrencyId) {
		Account account = find(id);
		long oldCurrencyId = account.getCurrency();
		double oldCurrencyCount = account.getCurrentCount();
		double currencyCount = iCurrencyExchanger.exchange(
				account.getCurrency(), toCurrencyId, account.getCurrentCount());
		account.setCurrency(toCurrencyId);
		account.setCurrentCount(currencyCount);
		addOrUpdateAccount(account);
		LOG.info("Currency changed: currency " + oldCurrencyId + " -> "
				+ account.getCurrency() + "; currencyCount  "
				+ oldCurrencyCount + " -> " + account.getCurrentCount());
	}

}
