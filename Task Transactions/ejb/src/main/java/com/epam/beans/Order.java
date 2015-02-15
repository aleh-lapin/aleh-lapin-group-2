package com.epam.beans;

public class Order extends Bean {
	private Ticket ticket;
	private Customer customer;
	private int state;
	public static final int PENDING = 0;
	public static final int CANCELED = 1;
	public static final int PAID = 2;
	
	public Order() {
		super();
		this.setState(PENDING);
	}

	public Order(Customer customer, Ticket ticket) {
		this();
		this.customer = customer;
		this.ticket = ticket;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((ticket == null) ? 0 : ticket.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (ticket == null) {
			if (other.ticket != null)
				return false;
		} else if (!ticket.equals(other.ticket))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [ticket=").append(ticket).append(", customer=")
				.append(customer).append("]");
		return builder.toString();
	}
	
	
}
