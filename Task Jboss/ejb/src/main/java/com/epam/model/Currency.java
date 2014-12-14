package com.epam.model;

public enum Currency {
	USD(0), EUR(1), RU(2);
	
	private long id;
	
	private Currency(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
}
