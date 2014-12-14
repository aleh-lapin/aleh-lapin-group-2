package com.epam.model;

import java.io.Serializable;

public class Account implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String user;
	private long currencyId;
	private double currentCount;
	
	public Account(long id, String user, long currencyId, double currentCount) {
		super();
		this.id = id;
		this.user = user;
		this.currencyId = currencyId;
		this.currentCount = currentCount;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public long getCurrency() {
		return currencyId;
	}
	
	public void setCurrency(long currencyId) {
		this.currencyId = currencyId;
	}
	
	public double getCurrentCount() {
		return currentCount;
	}
	
	public void setCurrentCount(double currentCount) {
		this.currentCount = currentCount;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (currencyId ^ (currencyId >>> 32));
		long temp;
		temp = Double.doubleToLongBits(currentCount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Account other = (Account) obj;
		if (currencyId != other.currencyId)
			return false;
		if (Double.doubleToLongBits(currentCount) != Double
				.doubleToLongBits(other.currentCount))
			return false;
		if (id != other.id)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", user=" + user + ", currencyId="
				+ currencyId + ", currentCount=" + currentCount + "]";
	}

}
