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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((toCurrencyRate == null) ? 0 : toCurrencyRate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrencyRate other = (CurrencyRate) obj;
		if (id != other.id)
			return false;
		if (toCurrencyRate == null) {
			if (other.toCurrencyRate != null)
				return false;
		} else if (!toCurrencyRate.equals(other.toCurrencyRate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CurrencyRate [id=" + id + ", toCurrencyRate=" + toCurrencyRate
				+ "]";
	}

}
