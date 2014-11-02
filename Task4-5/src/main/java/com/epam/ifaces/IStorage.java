package com.epam.ifaces;

import java.io.Serializable;

public interface IStorage<T extends Serializable, ID extends Serializable> {
	
	T find(ID id);
	void addOrUpdate(ID id, T Value);
	
}
