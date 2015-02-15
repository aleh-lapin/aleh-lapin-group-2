package com.epam.transactions;

public class TransactionController {
	
	public final static int NEW = 0;
	public final static int RUNING = 1;
	public final static int ABORTED = 2;
	public final static int FINISHED = 3;
	
	private int state;

	public TransactionController() {
		super();
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	
} 
