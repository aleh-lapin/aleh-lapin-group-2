package com.epam.service;

import java.util.List;

import javax.ejb.Local;

import com.epam.beans.Order;
import com.epam.beans.Ticket;
import com.epam.exceptions.ServiceException;

@Local
public interface OrderService {

	void init();
	Order order(String ticketId, String userId) throws ServiceException;
	List<Ticket> getTickets() throws ServiceException;
	long getBalance(String userId);

}
