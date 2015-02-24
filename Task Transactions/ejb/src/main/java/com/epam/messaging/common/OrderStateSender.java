package com.epam.messaging.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

import com.epam.beans.Order;
import com.epam.messaging.impl.SocketChannelAdapterImpl;
import com.epam.shared.EventConverter;

public class OrderStateSender {

	private static final Logger LOG = Logger.getLogger(OrderStateSender.class);
	private static final int QUEUE_SIZE = 100;
	private BlockingQueue<Order> queue = new ArrayBlockingQueue<Order>(QUEUE_SIZE);
	private boolean shutdown = false;
	private SocketChannelAdapterImpl adapter;
	private static OrderStateSender instance;
	
	private OrderStateSender(SocketChannelAdapterImpl channelAdapter) {
		adapter = channelAdapter;
	}
	
	public static OrderStateSender getInstance(SocketChannelAdapterImpl channelAdapter) {
		if (instance == null) {
			instance = new OrderStateSender(channelAdapter);
		}
		return instance;
	}

	private final class AsyncSender implements Runnable {

		/** {@inheritDoc} */
		public void run() {
			if (adapter != null) {
				while (!shutdown) {
					try {
						Order order = queue.take();
						adapter.publishMessage(EventConverter.convertOrderEvent(order));

						LOG.info("Message was successfully sent: " + order);
					} catch (IOException e) {
						LOG.error(e.fillInStackTrace());
						try {
							adapter.reconnectToChannel();
						} catch (IOException e1) {
							LOG.error(e.fillInStackTrace());
						}
					} catch (InterruptedException e) {
						LOG.error(e.fillInStackTrace());
					}
				}
			} else {
				LOG.error("Endpoint is unavailable! Shutting down!");
			}

			List<Order> orders = new ArrayList<Order>();
			queue.drainTo(orders);

			LOG.info("Unsent order events:");
			for (Order order: orders) {
				LOG.info(order);
			}
		}
	}

	/**
	 * Start sending.
	 */
	public void startSending() {
		new Thread(new AsyncSender()).start();
	}

	/**
	 * Shutdown.
	 */
	public void shutdown() {
		shutdown = true;
		try {
			adapter.diconectFromChannel();
		} catch (IOException e) {
			LOG.error("Error occured on closing Soccet Channel Adapter: "
					+ e.fillInStackTrace());
		}
	}

	public void handleOrderChange(Order order) {
		try {
			queue.put(order);
		} catch (InterruptedException e) {
			LOG.error(e.fillInStackTrace());
		}
	}
	
}
