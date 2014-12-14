package com.epam.tests.services;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.epam.ifaces.ICurrencyExchanger;
import com.epam.ifaces.IStorage;
import com.epam.impl.CurrencyRateStorage;
import com.epam.model.Currency;
import com.epam.model.CurrencyRate;

public class ICurrencyExchangerTest extends Assert {

	private IStorage<CurrencyRate, Long> iStorageCurrency;

	private Mockery context;
	private ICurrencyExchanger mockedICurrencyExchanger;

	@Before
	public void setUp() {
		iStorageCurrency = new CurrencyRateStorage();

		context = new Mockery();
		mockedICurrencyExchanger = context.mock(ICurrencyExchanger.class);
	}

	@SuppressWarnings("unused")
	@Test
	public void testExchange() {
		Map<Long, Double> toCurrencyRate = new HashMap<Long, Double>();
		toCurrencyRate.put(Currency.EUR.getId(), 1.22);
		toCurrencyRate.put(Currency.RU.getId(), 0.0277);
		final CurrencyRate currencyRate = new CurrencyRate(
				Currency.USD.getId(), toCurrencyRate);
		final double CURRENCY_COUNT = 1000;
		iStorageCurrency.addOrUpdate(currencyRate.getId(), currencyRate);
		context.checking(new Expectations() {
			{
				oneOf(mockedICurrencyExchanger).exchange(Currency.USD.getId(),
						Currency.EUR.getId(), CURRENCY_COUNT);
				will(returnValue(CURRENCY_COUNT
						* currencyRate.getToCurrencyRate().get(
								Currency.EUR.getId())));
			}
		});
		final double result = mockedICurrencyExchanger.exchange(
				Currency.USD.getId(), Currency.EUR.getId(), CURRENCY_COUNT);
	}

}
