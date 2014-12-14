package com.epam.ifaces;

import javax.ejb.Local;

import com.epam.model.Account;

@Local
public interface IAccount {

	Account findAccont(long id);
	void addOrUpdateAccount(Account account);
	void changeCurrencyCount(long id, double currencyCount);
	void changeCurrency(long id, long toCurrencyId);
	
}
