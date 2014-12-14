package com.epam.ifaces;

import java.util.List;

import javax.ejb.Local;

import com.epam.model.CurrencyRate;

@Local
public interface ICurrencyRate {
	
	List<CurrencyRate> list();
	CurrencyRate findCurrencyRate(long id);
	void addOrUpdateCurrencyRate(CurrencyRate currencyRate);
	
}
