package com.epam.dao;

import java.util.List;

import com.epam.beans.Order;

public interface OrderRepository {
	Long create(Order order);
	Order get(Long id);
	void update(Order order);
	void delete(Long id);
	List<Order> getAll();
}
