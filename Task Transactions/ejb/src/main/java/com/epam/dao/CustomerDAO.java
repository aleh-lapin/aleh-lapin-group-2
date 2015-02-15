package com.epam.dao;

import com.epam.beans.Customer;

public class CustomerDAO extends AbstractRepository<Customer> implements CustomerRepository {

	@Override
	public Long create(Customer customer) {
		return createItem(customer);
	}

	@Override
	public Customer get(Long id) {
		return read(id);
	}

	@Override
	public void update(Customer customer) {
		edit(customer);
		
	}

	@Override
	public void delete(Long id) {
		remove(read(id));		
	}


}
