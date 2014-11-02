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
	public String toString() {
		return "Account [id=" + id + ", user=" + user + ", currencyId="
				+ currencyId + ", currentCount=" + currentCount + "]";
	}

}
