package com.epam.transactions;

import java.util.ArrayList;
import java.util.List;

public class Coordinator {

	private List<AbstractTransaction> participants = new ArrayList<AbstractTransaction>();

	public Coordinator(List<AbstractTransaction> participants) {
		this.participants = participants;
	}

	public void startTransactions() {
		for(AbstractTransaction tr : participants) {
			tr.startTransaction();
		}
	}

	public void synchronize() {
		for(AbstractTransaction tr : participants) {
			tr.synchronize();
		}
	}

	public boolean getStatus() {
		boolean result = true;

		for(AbstractTransaction tr : participants) {
			if (tr.getControl().getState() == TransactionController.ABORTED) {
				result = false;
			}
		}

		return result;
	}

	public void abort() {
		for(AbstractTransaction tr : participants) {
			tr.abort();
		}
	}

	public void commit() {
		for(AbstractTransaction tr : participants) {
			tr.commit();
		}
	}

}
