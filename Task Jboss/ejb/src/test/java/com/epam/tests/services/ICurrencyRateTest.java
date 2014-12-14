package com.epam.tests.services;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.epam.ifaces.ICurrencyRate;
import com.epam.ifaces.IStorage;
import com.epam.impl.CurrencyRateServiceImpl;
import com.epam.impl.CurrencyRateStorage;
import com.epam.model.Currency;
import com.epam.model.CurrencyRate;

public class ICurrencyRateTest extends Assert{
	
	private ICurrencyRate iCurrencyRate;
	private IStorage<CurrencyRate, Long> iStorageCurrency;
	
	private Mockery context;
	private ICurrencyRate mockedICurrencyRate;
	
	@Before
	public void setUp() {
		iStorageCurrency = new CurrencyRateStorage();
		iCurrencyRate = new CurrencyRateServiceImpl(iStorageCurrency);
		
		context = new Mockery();
		mockedICurrencyRate = context.mock(ICurrencyRate.class);
	}
	
	@Test
	public void testCreate() {
		Map<Long, Double> toCurrencyRate = new HashMap<Long, Double>();
		toCurrencyRate.put(Currency.EUR.getId(), 1.22);
		toCurrencyRate.put(Currency.RU.getId(), 0.0277);
		final CurrencyRate currencyRate = new CurrencyRate(Currency.USD.getId(),
				toCurrencyRate);
		context.checking(new Expectations() {{
			oneOf(mockedICurrencyRate).addOrUpdateCurrencyRate(currencyRate);
		}});
		mockedICurrencyRate.addOrUpdateCurrencyRate(currencyRate);
	}
	
	@Test
	public void testFind() {
		Map<Long, Double> toCurrencyRate = new HashMap<Long, Double>();
		toCurrencyRate.put(Currency.EUR.getId(), 1.22);
		toCurrencyRate.put(Currency.RU.getId(), 0.0277);
		CurrencyRate currencyRate = new CurrencyRate(Currency.USD.getId(),
				toCurrencyRate);
		iCurrencyRate.addOrUpdateCurrencyRate(currencyRate);
		assertEquals(currencyRate, iCurrencyRate.findCurrencyRate(Currency.USD.getId()));
	}
	
}
