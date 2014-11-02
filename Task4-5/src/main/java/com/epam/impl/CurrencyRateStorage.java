package com.epam.impl;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.epam.ifaces.ICurrencyExchanger;
import com.epam.ifaces.ICurrencyRate;
import com.epam.model.CurrencyRate;

public class CurrencyRateStorage extends
		AbstractFileStorage<CurrencyRate, Long> implements ICurrencyRate, ICurrencyExchanger {
	private static final Logger LOG = Logger.getLogger(CurrencyRateStorage.class);
	
	private String filePath;
	
	public CurrencyRateStorage() {
		File file = new File("currencyrate.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		this.filePath = file.getAbsolutePath();
	}
	
	@Override
	protected String getFilePath() {
		return filePath;
	}

	public synchronized CurrencyRate findCurrencyRate(long id) {
		return find(id);
	}
	
	public synchronized void addOrUpdateCurrencyRate(CurrencyRate currencyRate) {
		addOrUpdate(currencyRate.getId(), currencyRate);
		LOG.info("Create or Updated: " + currencyRate);
	}

	public synchronized CurrencyRate changeExchageRate(long from, long to,
			double exchangeRate) {
		CurrencyRate currencyRate = find(from);
		currencyRate.getToCurrencyRate().put(to, exchangeRate);
		addOrUpdateCurrencyRate(currencyRate);
		return currencyRate;
	}

	public synchronized double exchange(long from, long to, double currencyCount) {
		CurrencyRate currencyRate = find(from);
		double rate = currencyRate.getToCurrencyRate().get(to);
		return currencyCount * rate;
		
	}

}
