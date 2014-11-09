package com.epam.tests.dao;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.epam.ifaces.IStorage;
import com.epam.model.Account;
import com.epam.model.Currency;
import com.epam.model.CurrencyRate;

public class FileStorageTest extends Assert {

	private Mockery context;
	private IStorage<Account, Long> mockedIStorageAccount;
	private IStorage<CurrencyRate, Long> mockedIStorageCurrency;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		context = new Mockery();
		mockedIStorageAccount = context.mock(IStorage.class, "mockedIStorageAccount");
		mockedIStorageCurrency = context.mock(IStorage.class, "mockedIStorageCurrency");
	}
	
	@Test
	public void testCreate() {
		final Account account = new Account(0, "Test", 0, 0);
		Map<Long, Double> toCurrencyRate = new HashMap<Long, Double>();
		toCurrencyRate.put(Currency.EUR.getId(), 1.22);
		toCurrencyRate.put(Currency.RU.getId(), 0.0277);
		final CurrencyRate currencyRate = new CurrencyRate(
				Currency.USD.getId(), toCurrencyRate);
		context.checking(new Expectations() {{
			oneOf(mockedIStorageAccount).addOrUpdate(account.getId(),account);
			oneOf(mockedIStorageCurrency).addOrUpdate(currencyRate.getId(),currencyRate);
		}});
		mockedIStorageAccount.addOrUpdate(account.getId(),account);
		mockedIStorageCurrency.addOrUpdate(currencyRate.getId(),currencyRate);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testFind() {
		final Account account = new Account(0, "Test", 0, 0);
		Map<Long, Double> toCurrencyRate = new HashMap<Long, Double>();
		toCurrencyRate.put(Currency.EUR.getId(), 1.22);
		toCurrencyRate.put(Currency.RU.getId(), 0.0277);
		final CurrencyRate currencyRate = new CurrencyRate(
				Currency.USD.getId(), toCurrencyRate);
		context.checking(new Expectations() {{
			oneOf(mockedIStorageAccount).addOrUpdate(account.getId(),account);
			oneOf(mockedIStorageCurrency).addOrUpdate(currencyRate.getId(),currencyRate);
			oneOf(mockedIStorageAccount).find(account.getId());
			will(returnValue(account));
			oneOf(mockedIStorageCurrency).find(currencyRate.getId());
			will(returnValue(currencyRate));
		}});
		mockedIStorageAccount.addOrUpdate(account.getId(),account);
		mockedIStorageCurrency.addOrUpdate(currencyRate.getId(),currencyRate);
		Account returnedAccount = mockedIStorageAccount.find(account.getId());
		CurrencyRate returnedCurrencyRate = mockedIStorageCurrency.find(currencyRate.getId());
	}
	
}
