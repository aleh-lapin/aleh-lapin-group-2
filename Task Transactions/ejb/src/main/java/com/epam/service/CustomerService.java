package com.epam.service;

import com.epam.beans.CustomerWrapper;

public interface CustomerService {

	CustomerWrapper findCustomer(Long Id);
	
}
