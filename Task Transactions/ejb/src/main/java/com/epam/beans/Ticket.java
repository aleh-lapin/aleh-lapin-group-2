package com.epam.beans;

import java.util.Date;

public class Ticket extends Bean{
	private int price;
	private String name;
	private int seat;
	private int row;
	private Date date;
	private Customer cutomer;
	private boolean isOrdered;
	public Ticket() {
		super();
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seat) {
		this.seat = seat;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Customer getCutomer() {
		return cutomer;
	}
	public void setCutomer(Customer cutomer) {
		this.cutomer = cutomer;
	}
	public boolean getOrdered() {
		return isOrdered;
	}
	public void setOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cutomer == null) ? 0 : cutomer.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + price;
		result = prime * result + row;
		result = prime * result + seat;
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
		Ticket other = (Ticket) obj;
		if (cutomer == null) {
			if (other.cutomer != null)
				return false;
		} else if (!cutomer.equals(other.cutomer))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price != other.price)
			return false;
		if (row != other.row)
			return false;
		if (seat != other.seat)
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ticket [price=").append(price).append(", name=")
				.append(name).append(", seat=").append(seat).append(", row=")
				.append(row).append(", date=").append(date)
				.append(", cutomer=").append(cutomer).append("]");
		return builder.toString();
	}
	
	
}
