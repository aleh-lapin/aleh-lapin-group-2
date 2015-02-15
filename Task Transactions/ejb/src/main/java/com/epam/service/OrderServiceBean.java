package com.epam.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.epam.beans.Customer;
import com.epam.beans.Order;
import com.epam.beans.Ticket;
import com.epam.dao.CustomerRepository;
import com.epam.dao.OrderRepository;
import com.epam.dao.TicketRepository;
import com.epam.exceptions.ServiceException;
import com.epam.transactions.CompositeTransaction;

@Stateless
public class OrderServiceBean implements OrderService {

	@Inject
	CustomerRepository customerDAO;
		
	@Inject
	TicketRepository ticketDAO;
	
	@Inject
	OrderRepository orderDAO;

	@Override
	public void init() {
		Customer customer = new Customer();
    	customer.setName("Test Customer");
    	customer.setBalance((long)50000);
    	customerDAO.create(customer);
    	
    	Ticket ticket1 = new Ticket();
    	ticket1.setName("Ticket1");
    	ticket1.setPrice(10000);
    	ticketDAO.create(ticket1);
    	
    	Ticket ticket2 = new Ticket();
    	ticket2.setName("Ticket2");
    	ticket2.setPrice(15000);
    	ticketDAO.create(ticket2);	
	}
	
	@Override
	public Order order(String ticketIds, String userIds) throws ServiceException {
		Long ticketId = Long.parseLong(ticketIds);
		Long userId = Long.parseLong(userIds);
		Customer customer = customerDAO.get(userId);
		Ticket ticket = ticketDAO.get(ticketId);
		Order order = null;
		if(customer.getBalance() - ticket.getPrice() > 0) {
			order = new Order(customer, ticket);
		    CompositeTransaction ct = new CompositeTransaction(order, orderDAO, customerDAO, ticketDAO);
		    ct.processTransactions();
		}
		
		return order;
	}

	@Override
	public List<Ticket> getTickets() throws ServiceException {
		return ticketDAO.getAll();
	}

	@Override
	public long getBalance(String userId) {
		Long id = Long.parseLong(userId); 
		return customerDAO.get(id).getBalance();
	}

}
