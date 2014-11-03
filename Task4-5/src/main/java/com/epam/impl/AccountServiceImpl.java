package com.epam.impl;

import org.apache.log4j.Logger;

import com.epam.ifaces.IAccount;
import com.epam.ifaces.ICurrencyExchanger;
import com.epam.ifaces.IStorage;
import com.epam.model.Account;

public class AccountServiceImpl implements IAccount {
	private static final Logger LOG = Logger
			.getLogger(AccountServiceImpl.class);

	private IStorage<Account, Long> iStorage;
	private ICurrencyExchanger iCurrencyExchanger;

	public AccountServiceImpl(IStorage<Account, Long> iStorage, ICurrencyExchanger iCurrencyExchanger) {
		this.iStorage = iStorage;
		this.iCurrencyExchanger = iCurrencyExchanger;
	}

	public synchronized Account findAccont(long id) {
		return iStorage.find(id);
	}

	public synchronized void addOrUpdateAccount(Account account) {
		iStorage.addOrUpdate(account.getId(), account);

	}

	public synchronized void changeCurrencyCount(long id, double currencyCount) {
		Account account = findAccont(id);
		double oldCurrencyCount = account.getCurrentCount();
		account.setCurrentCount(currencyCount);
		addOrUpdateAccount(account);
		LOG.info("Currency count changed: currencyCount  " + oldCurrencyCount
				+ " -> " + account.getCurrentCount());

	}

	public synchronized void changeCurrency(long id, long toCurrencyId) {
		Account account = findAccont(id);
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
