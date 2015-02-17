package com.epam.transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.epam.beans.Order;
import com.epam.dao.CustomerRepository;
import com.epam.dao.OrderRepository;
import com.epam.dao.TicketRepository;

public class CompositeTransaction {
	Logger LOG = Logger.getLogger(CompositeTransaction.class);
	
	private Coordinator coordinator;
	private static Lock l = new ReentrantLock();

	public CompositeTransaction(Order order, OrderRepository orderRepo,
			CustomerRepository customerRepo, TicketRepository ticketRepo) {
		CheckOutTransaction ct = new CheckOutTransaction(order, orderRepo, customerRepo, ticketRepo);
		List<AbstractTransaction> participants = new ArrayList<AbstractTransaction>();
		participants.add(ct);

		this.coordinator = new Coordinator(participants);
	}

	public void processTransactions(){
		LOG.info("Processing started");
		l.lock();
		try {
			LOG.info("Start");
			coordinator.startTransactions();
			LOG.info("Synch");
			coordinator.synchronize();
			if (coordinator.getStatus()) {
				LOG.info("Commit");
				coordinator.commit();
				LOG.info("Synch");
				coordinator.synchronize();
				
				if (!coordinator.getStatus()) {
					coordinator.abort();
				}
			} else {
				coordinator.abort();
			}
		} finally {
			l.unlock();
		}
		LOG.info("Processing ended");
	}
}
