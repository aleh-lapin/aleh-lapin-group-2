package com.epam.service;

import com.epam.beans.Customer;
import com.epam.beans.CustomerWrapper;

public class CustomerServiceAdapter {

	public static CustomerWrapper translate(Customer customer) {
		CustomerWrapper wrapper = null;
		if (customer != null) {
			wrapper = new CustomerWrapper();
			//ACL logic here
			wrapper.setId(customer.getId());
			wrapper.setName(customer.getName());
			wrapper.setBalance(customer.getBalance());
		}
		return wrapper;
	}
	
}
