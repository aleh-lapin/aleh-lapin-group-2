package com.epam.utils;

import java.util.HashMap;
import java.util.Map;

import com.epam.model.Currency;
import com.epam.model.CurrencyRate;

public class CurrencyRateFactory {

	public static CurrencyRate createCurrencyRate(long id) {
		for (Currency carrency : Currency.values()) {
			if (carrency.getId() == id) {
				switch (carrency) {
				case USD:
					return initUSD();
				case EUR:
					return initEUR();
				case RU:
					return initRU();
				}
			}
		}
		throw new IllegalArgumentException("This type of currency is not supported, id = " + id);
	}
	
	private static CurrencyRate initUSD() {
		Map<Long, Double> toCurrencyRate = new HashMap<Long, Double>();
		toCurrencyRate.put(Currency.EUR.getId(), 0.8);
		toCurrencyRate.put(Currency.RU.getId(), 37.0);
		return new CurrencyRate(Currency.USD.getId(), toCurrencyRate);
	}

	private static CurrencyRate initEUR() {
		Map<Long, Double> toCurrencyRate = new HashMap<Long, Double>();
		toCurrencyRate.put(Currency.USD.getId(), 1.25);
		toCurrencyRate.put(Currency.RU.getId(), 45.0);
		return new CurrencyRate(Currency.EUR.getId(), toCurrencyRate);
	}

	private static CurrencyRate initRU() {
		Map<Long, Double> toCurrencyRate = new HashMap<Long, Double>();
		toCurrencyRate.put(Currency.USD.getId(), 0.027);
		toCurrencyRate.put(Currency.EUR.getId(), 0.022);
		return new CurrencyRate(Currency.RU.getId(), toCurrencyRate);
	}
	
}
