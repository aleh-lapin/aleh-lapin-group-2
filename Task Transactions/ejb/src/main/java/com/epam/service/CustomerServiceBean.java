package com.epam.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.epam.beans.CustomerWrapper;
import com.epam.dao.CustomerDAO;

@Stateless
public class CustomerServiceBean implements CustomerService {

	@Inject
	private CustomerDAO customerDAO;
	
	@Override
	public CustomerWrapper findCustomer(Long Id) {
		return CustomerServiceAdapter.translate(customerDAO.get(Id));
	}

	
	
}
