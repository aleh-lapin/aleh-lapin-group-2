package com.epam.ifaces;

import javax.ejb.Local;

@Local
public interface ICurrencyExchanger {

	double exchange(long from, long to, double currencyCount);

}
