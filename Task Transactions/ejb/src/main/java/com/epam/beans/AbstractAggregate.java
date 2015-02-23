package com.epam.beans;

import java.util.List;

public abstract class AbstractAggregate<T> {

	private Long id;
	private List<T> items;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<T> getItems() {
		return items;
	}
	
	public void setItems(List<T> items) {
		this.items = items;
	}
	
}
