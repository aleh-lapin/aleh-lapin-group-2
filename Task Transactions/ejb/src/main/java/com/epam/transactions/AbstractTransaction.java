package com.epam.transactions;

import java.util.UUID;
import org.apache.log4j.Logger;
import com.epam.beans.Bean;

public abstract class AbstractTransaction implements Participant {
	
	private static final long SLEEP_TIMEOUT = 50;
	private static final Logger LOG = Logger.getLogger(AbstractTransaction.class);;
	private UUID id;
	private TransactionController control;
	private Bean savedState; 
	
	public TransactionController getControl() {
		return control;
	}

	public void setControl(TransactionController control) {
		this.control = control;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Bean getSavedState() {
		return savedState;
	}

	public void saveState(Bean bean) {
		this.savedState = bean;
	}

	public AbstractTransaction() {
		super();
		this.setId(UUID.randomUUID());
	}
	
	protected abstract void startTransactionOperation();
	protected abstract void commitOperation();
	protected abstract void abortOperation();
	
	public void startTransaction() {
		getControl().setState(TransactionController.RUNING);
		new Thread(){
			public void run() {
				try {
					startTransactionOperation();
					getControl().setState(TransactionController.FINISHED);
				} catch (Exception e) {
					LOG.error(e);
					getControl().setState(TransactionController.ABORTED);
				}
			}
		}.start();
	}

	public void abort() {
		getControl().setState(TransactionController.ABORTED);
		new Thread(){
			public void run() {
				abortOperation();
			}
		}.start();
	}

	public void commit() {
		getControl().setState(TransactionController.RUNING);
		new Thread(){
			public void run() {
				try {
					commitOperation();
					getControl().setState(TransactionController.FINISHED);
				} catch (Exception e) {
					LOG.error(e);
					getControl().setState(TransactionController.ABORTED);
				}
			}
		}.start();
	}

	public boolean synchronize() {
		while (control.getState() == TransactionController.RUNING) {
			try {
				Thread.sleep(SLEEP_TIMEOUT);
			} catch (InterruptedException e) {
				LOG.error("Error while waiting for response from StartTransaction Thread: " + 
						e.fillInStackTrace());
			}
		}
		return false;		
	}
}
