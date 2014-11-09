package com.epam.ifaces;

import com.epam.model.CurrencyRate;

public interface ICurrencyRate {
	
	CurrencyRate findCurrencyRate(long id);
	void addOrUpdateCurrencyRate(CurrencyRate currencyRate);
	
}
