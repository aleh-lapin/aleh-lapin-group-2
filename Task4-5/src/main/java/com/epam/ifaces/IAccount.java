package com.epam.ifaces;

import com.epam.model.Account;

public interface IAccount {

	Account findAccont(long id);
	void addOrUpdateAccount(Account account);
	void changeCurrencyCount(long id, double currencyCount);
	void changeCurrency(long id, long toCurrencyId);
	
}
