package com.epam.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.epam.ifaces.ICurrencyExchanger;
import com.epam.ifaces.ICurrencyRate;
import com.epam.ifaces.IStorage;
import com.epam.model.CurrencyRate;
import com.epam.utils.CurrencyRateFactory;

@Stateful
public class CurrencyRateServiceImpl implements ICurrencyRate, ICurrencyExchanger{
	private static final Logger LOG = Logger.getLogger(CurrencyRateServiceImpl.class);
	
	@Inject
	private IStorage<CurrencyRate, Long> iStorage;
	
	public CurrencyRateServiceImpl() {
	}
	
	public CurrencyRateServiceImpl(IStorage<CurrencyRate, Long> iStorage) {
		this.iStorage = iStorage;
	}
	
	public synchronized List<CurrencyRate> list() {
		return iStorage.list();
	}
	
	public synchronized CurrencyRate findCurrencyRate(long id) {
		CurrencyRate currencyRate = iStorage.find(id);
		if (currencyRate == null) {
			currencyRate = CurrencyRateFactory.createCurrencyRate(id);
			iStorage.addOrUpdate(id, currencyRate);
		}
		return currencyRate;
	}

	public synchronized void addOrUpdateCurrencyRate(CurrencyRate currencyRate) {
		iStorage.addOrUpdate(currencyRate.getId(), currencyRate);
		LOG.info("Create or Updated: " + currencyRate);
	}

	public synchronized double exchange(long from, long to, double currencyCount) {
		CurrencyRate currencyRate = findCurrencyRate(from);
		double rate = currencyRate.getToCurrencyRate().get(to);
		return currencyCount * rate;
	}

}
