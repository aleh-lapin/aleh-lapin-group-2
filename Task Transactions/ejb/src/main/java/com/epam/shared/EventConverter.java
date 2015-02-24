package com.epam.shared;

import com.epam.beans.Order;

public final class EventConverter {

	private EventConverter() { }

	public static OrderEventVO convertOrderEvent(Order order) {
		OrderEventVO result = new OrderEventVO();
		result.setOrderId(order.getId() != null ? order.getId() : -1);
		result.setState(order.getState());
		result.setCustomerName(order.getCustomer().getName());
		result.setTicketName(order.getTicket().getName());
		result.setPrice(order.getTicket().getPrice());
		return result;
	}

}
