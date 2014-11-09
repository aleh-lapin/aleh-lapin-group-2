package com.epam.tests.services;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.epam.ifaces.IAccount;
import com.epam.ifaces.ICurrencyExchanger;
import com.epam.ifaces.IStorage;
import com.epam.impl.AccountFileStorage;
import com.epam.impl.AccountServiceImpl;
import com.epam.impl.CurrencyRateServiceImpl;
import com.epam.impl.CurrencyRateStorage;
import com.epam.model.Account;
import com.epam.model.Currency;
import com.epam.model.CurrencyRate;

public class IAccountTest extends Assert {

	private IAccount iAccount;
	private ICurrencyExchanger iCurrencyExchanger;
	private IStorage<Account, Long> iStorageAccount;
	private IStorage<CurrencyRate, Long> iStorageCurrency;

	private Mockery context;;
	private IAccount mockedIAcount;

	@Before
	public void setUp() {
		iStorageAccount = new AccountFileStorage();
		iStorageCurrency = new CurrencyRateStorage();
		iCurrencyExchanger = new CurrencyRateServiceImpl(iStorageCurrency);
		iAccount = new AccountServiceImpl(iStorageAccount, iCurrencyExchanger);

		context = new Mockery();
		mockedIAcount = context.mock(IAccount.class);
	}

	@Test
	public void testCreate() {
		final Account account = new Account(0, "Test", 0, 0);
		context.checking(new Expectations() {
			{
				oneOf(mockedIAcount).addOrUpdateAccount(account);
			}
		});
		mockedIAcount.addOrUpdateAccount(account);
	}

	@Test
	public void testFind() {
		Account account = new Account(0, "Test", 0, 0);
		iAccount.addOrUpdateAccount(account);
		assertEquals(account, iAccount.findAccont(0));
	}

	@Test
	public void testChangeCurrencyCount() {
		final double CURRENCY_COUNT = 1000;
		Account account = new Account(0, "Test", 0, 0);
		iAccount.addOrUpdateAccount(account);
		iAccount.changeCurrencyCount(0, CURRENCY_COUNT);
		assertEquals(account.getCurrentCount() + CURRENCY_COUNT, iAccount
				.findAccont(0).getCurrentCount(), 0.0);
	}

	@Test
	public void testChangeCurrency() {
		Map<Long, Double> toCurrencyRate = new HashMap<Long, Double>();
		toCurrencyRate.put(Currency.USD.getId(), 0.88);
		toCurrencyRate.put(Currency.RU.getId(), 0.0219);
		CurrencyRate currencyRate = new CurrencyRate(Currency.EUR.getId(),
				toCurrencyRate);
		iStorageCurrency.addOrUpdate(currencyRate.getId(), currencyRate);
		Account account = new Account(0, "Test", Currency.EUR.getId(), 0);
		iAccount.addOrUpdateAccount(account);
		iAccount.changeCurrency(0, Currency.USD.getId());
		assertEquals(Currency.USD.getId(), iAccount.findAccont(0).getCurrency());
	}

}
