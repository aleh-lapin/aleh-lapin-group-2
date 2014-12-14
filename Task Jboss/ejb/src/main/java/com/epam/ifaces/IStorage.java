package com.epam.ifaces;

import java.io.Serializable;
import java.util.List;

public interface IStorage<T extends Serializable, ID extends Serializable> {
	
	List<T> list();
	T find(ID id);
	void addOrUpdate(ID id, T Value);
	
}
