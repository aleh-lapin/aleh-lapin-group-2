package com.epam.dao;

import java.util.List;

import com.epam.beans.Order;

public class OrderDAO extends AbstractRepository<Order> implements OrderRepository {

	@Override
	public Long create(Order order) {
		return createItem(order);
	}

	@Override
	public Order get(Long id) {
		return read(id);
	}

	@Override
	public void update(Order order) {
		edit(order);
	}

	@Override
	public void delete(Long id) {
		remove(read(id));
	}

	@Override
	public List<Order> getAll() {
		return null;
	}

}
