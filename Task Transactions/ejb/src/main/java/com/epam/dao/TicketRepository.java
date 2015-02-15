package com.epam.dao;

import java.util.List;

import com.epam.beans.Ticket;

public interface TicketRepository {
	Long create(Ticket ticket);
	Ticket get(Long id);
	void update(Ticket ticket);
	void delete(Long id);
	List<Ticket> getAll();
}
