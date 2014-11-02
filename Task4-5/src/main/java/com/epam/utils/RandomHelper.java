package com.epam.utils;

import java.util.Random;

import com.epam.model.Currency;

public class RandomHelper {

	private static Random random = new Random();
	
	public static long getRandomCurrency() {
		return Long.valueOf(random.nextInt(Currency.values().length));
	}
	
	public static double getRandomCurrencyCount(double currencyCount, double variance) {
		return currencyCount + random.nextGaussian() * variance;
	}
	
	public static long getRandomCurrency(long currencyId) {
		long newCurrencyId = Long.valueOf(random.nextInt(Currency.values().length));
		while (currencyId == newCurrencyId) {
			newCurrencyId = Long.valueOf(random.nextInt(Currency.values().length));
		}
		return newCurrencyId;
	}
	
}
