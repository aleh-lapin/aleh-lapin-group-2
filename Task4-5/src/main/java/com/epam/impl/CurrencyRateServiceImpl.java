package com.epam.impl;

import org.apache.log4j.Logger;

import com.epam.ifaces.ICurrencyExchanger;
import com.epam.ifaces.ICurrencyRate;
import com.epam.ifaces.IStorage;
import com.epam.model.CurrencyRate;

public class CurrencyRateServiceImpl implements ICurrencyRate, ICurrencyExchanger{
	private static final Logger LOG = Logger.getLogger(CurrencyRateServiceImpl.class);
	
	private IStorage<CurrencyRate, Long> iStorage;
	
	public CurrencyRateServiceImpl(IStorage<CurrencyRate, Long> iStorage) {
		this.iStorage = iStorage;
	}
	
	public synchronized CurrencyRate findCurrencyRate(long id) {
		return iStorage.find(id);
	}

	public synchronized void addOrUpdateCurrencyRate(CurrencyRate currencyRate) {
		iStorage.addOrUpdate(currencyRate.getId(), currencyRate);
		LOG.info("Create or Updated: " + currencyRate);
	}

	public synchronized CurrencyRate changeExchageRate(long from, long to,
			double exchangeRate) {
		CurrencyRate currencyRate = findCurrencyRate(from);
		currencyRate.getToCurrencyRate().put(to, exchangeRate);
		addOrUpdateCurrencyRate(currencyRate);
		return currencyRate;
	}

	public synchronized double exchange(long from, long to, double currencyCount) {
		CurrencyRate currencyRate = findCurrencyRate(from);
		double rate = currencyRate.getToCurrencyRate().get(to);
		return currencyCount * rate;
	}

}
