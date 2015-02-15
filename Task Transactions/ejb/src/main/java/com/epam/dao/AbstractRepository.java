package com.epam.dao;

import java.util.HashMap;
import java.util.Map;

import com.epam.beans.Bean;

public abstract class AbstractRepository<T extends Bean> {
	
	private long sequence = 0;
    protected final Map<Long, T> registry = new HashMap<Long, T>();
    
    public AbstractRepository() {
    	this(0);
    }

	public AbstractRepository(long i) {
		super();
		this.sequence = i;
	}

    protected final Long nextPk() {
        return ++sequence;
    }
    
	public Long createItem(T item) {
		Long id = nextPk();     
		item.setId(id);      
		registry.put(id, item);       
        return id;
	}

	public T read(Long id) {
		return registry.get(id);
	}

	public T edit(T item) {
		return registry.put(item.getId(), item);
	}

	public void remove(T item) {
		registry.remove(item.getId());
	}
}
