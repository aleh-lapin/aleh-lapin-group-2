package com.epam.model;

import java.io.Serializable;
import java.util.Map;

public class CurrencyRate implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long id;
	private Map<Long, Double> toCurrencyRate;
	
	public CurrencyRate(long id, Map<Long, Double> toCurrencyRate) {
		super();
		this.id = id;
		this.toCurrencyRate = toCurrencyRate;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Map<Long, Double> getToCurrencyRate() {
		return toCurrencyRate;
	}
	
	public void setToCurrencyRate(Map<Long, Double> toCurrencyRate) {
		this.toCurrencyRate = toCurrencyRate;
	}

	@Override
	public String toString() {
		return "CurrencyRate [id=" + id + ", toCurrencyRate=" + toCurrencyRate
				+ "]";
	}

}
