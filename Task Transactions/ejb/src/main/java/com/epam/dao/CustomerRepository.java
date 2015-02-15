package com.epam.dao;

import com.epam.beans.Customer;

public interface CustomerRepository {
	Long create(Customer customer);
	Customer get(Long id);
	void update(Customer customer);
	void delete(Long id);
}
