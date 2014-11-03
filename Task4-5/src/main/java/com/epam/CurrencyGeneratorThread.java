package com.epam;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.epam.ifaces.ICurrencyRate;
import com.epam.model.Currency;
import com.epam.model.CurrencyRate;

public class CurrencyGeneratorThread extends Thread {
	private static final Logger LOG = Logger.getLogger(CurrencyGeneratorThread.class);
	
	private ICurrencyRate iCurrencyRate;

	public CurrencyGeneratorThread(String name, ICurrencyRate iCurrencyRate) {
		super(name);
		this.iCurrencyRate = iCurrencyRate;
	}

	@Override
	public void run() {
		initUSD();
		initEUR();
		initRU();
		try {
			while (!isInterrupted()) {
				changeRate();

				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			LOG.info("CurrencyGeneratorThread is stopped");
		}

	}

	private void changeRate() {
		Random random = new Random();
		Long currencyId = Long
				.valueOf(random.nextInt(Currency.values().length));
		Long currencyIdTo = Long
				.valueOf(random.nextInt(Currency.values().length));
		while (currencyId.equals(currencyIdTo)) {
			currencyIdTo = Long
					.valueOf(random.nextInt(Currency.values().length));
		}
		CurrencyRate currencyRate = iCurrencyRate.findCurrencyRate(currencyId);
		double newRate = getGaussian(random, currencyRate.getToCurrencyRate()
				.get(currencyIdTo));
		currencyRate.getToCurrencyRate().put(currencyIdTo, newRate);
		iCurrencyRate.addOrUpdateCurrencyRate(currencyRate);

	}

	private double getGaussian(Random random, double mean) {
		final double VARIANCE = 5.0f;
		return mean * (1 + random.nextGaussian() * VARIANCE / 100);
	}

	private void initUSD() {
		Map<Long, Double> toCurrencyRate = new HashMap<Long, Double>();
		toCurrencyRate.put(Currency.EUR.getId(), 1.22);
		toCurrencyRate.put(Currency.RU.getId(), 0.0277);
		CurrencyRate currencyRate = new CurrencyRate(Currency.USD.getId(),
				toCurrencyRate);
		iCurrencyRate.addOrUpdateCurrencyRate(currencyRate);
	}

	private void initEUR() {
		Map<Long, Double> toCurrencyRate = new HashMap<Long, Double>();
		toCurrencyRate.put(Currency.USD.getId(), 0.88);
		toCurrencyRate.put(Currency.RU.getId(), 0.0219);
		CurrencyRate currencyRate = new CurrencyRate(Currency.EUR.getId(),
				toCurrencyRate);
		iCurrencyRate.addOrUpdateCurrencyRate(currencyRate);
	}

	private void initRU() {
		Map<Long, Double> toCurrencyRate = new HashMap<Long, Double>();
		toCurrencyRate.put(Currency.USD.getId(), 36.1);
		toCurrencyRate.put(Currency.EUR.getId(), 45.6);
		CurrencyRate currencyRate = new CurrencyRate(Currency.RU.getId(),
				toCurrencyRate);
		iCurrencyRate.addOrUpdateCurrencyRate(currencyRate);
	}

}
