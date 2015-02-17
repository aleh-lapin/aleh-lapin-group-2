package com.epam.transactions;

import com.epam.beans.Customer;
import com.epam.beans.Order;
import com.epam.dao.CustomerRepository;
import com.epam.dao.OrderRepository;
import com.epam.dao.TicketRepository;

public class CheckOutTransaction extends AbstractTransaction {

	private Order order;

	private OrderRepository orderRepo;

	private CustomerRepository customerRepo;

	private TicketRepository ticketRepo;

	public CheckOutTransaction() {

	}

	public CheckOutTransaction(Order order, OrderRepository orderRepo,
			CustomerRepository customerRepo, TicketRepository ticketRepo) {
		this.order = order;
		this.orderRepo = orderRepo;
		this.customerRepo = customerRepo;
		this.ticketRepo = ticketRepo;
		setControl(new TransactionController());
	}

	@Override
	protected void startTransactionOperation() {
		order.getTicket().setOrdered(true);
		order.setState(Order.PENDING);
		Customer customer = order.getCustomer();
		long newBalance = customer.getBalance() - order.getTicket().getPrice();
		customer.setBalance(newBalance);
		//For test purposes
		//throw new NullPointerException();
	}

	@Override
	protected void commitOperation() {
		order.setState(Order.PAID);
		orderRepo.create(order);
		customerRepo.update(order.getCustomer());
		ticketRepo.update(order.getTicket());

	}

	@Override
	protected void abortOperation() {
		order.getTicket().setOrdered(false);
		order.setState(Order.CANCELED);
		Customer customer = order.getCustomer();
		long oldBalance = customer.getBalance() + order.getTicket().getPrice();
		customer.setBalance(oldBalance);
	}

}
