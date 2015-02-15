package com.epam.dao;

import java.util.ArrayList;
import java.util.List;

import com.epam.beans.Ticket;

public class TicketDAO extends AbstractRepository<Ticket> implements
		TicketRepository {

	@Override
	public Long create(Ticket ticket) {
		return createItem(ticket);
	}

	@Override
	public Ticket get(Long id) {
		return read(id);
	}

	@Override
	public void update(Ticket ticket) {
		edit(ticket);
	}

	@Override
	public void delete(Long id) {
		remove(read(id));
	}

	@Override
	public List<Ticket> getAll() {
		return new ArrayList<Ticket>(registry.values());
	}

}
