package com.epam.shared;

import java.io.Serializable;

public class OrderEventVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long orderId;
	private String customerName;
	private String ticketName;
	private long price;
	private long state;
	
	public long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getTicketName() {
		return ticketName;
	}
	
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	
	public long getPrice() {
		return price;
	}
	
	public void setPrice(long price) {
		this.price = price;
	}
	
	public long getState() {
		return state;
	}
	
	public void setState(long state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "OrderEventVO [orderId=" + orderId + ", customerName="
				+ customerName + ", ticketName=" + ticketName + ", price="
				+ price + ", state=" + state + "]";
	}
	
}
