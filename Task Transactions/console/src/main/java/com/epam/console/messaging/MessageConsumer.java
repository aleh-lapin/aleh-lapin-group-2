package com.epam.console.messaging;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.epam.shared.OrderEventVO;

public class MessageConsumer {

	private static final Logger LOG = Logger.getLogger(MessageConsumer.class);

	private MessagingEndpoint<OrderEventVO> endpoint;
	private boolean shutdown = false;

	public MessageConsumer(MessagingEndpoint<OrderEventVO> endpoint) {
		this.endpoint = endpoint;
	}

	private final class AsyncConsumer implements Runnable {

		@Override
		public void run() {

			if (endpoint != null) {
				while (!shutdown) {
					try {
						OrderEventVO event = endpoint.readMessage();

						if (event != null) {
							LOG.info("Following EVENT received: " + event);
						} else {
							LOG.warn("NULL event received");
						}
					} catch (ClassNotFoundException | IOException
							| InterruptedException e) {
						LOG.error("Exception while reading message: " + e.fillInStackTrace());
					}
				}
			} else {
				LOG.error("Enpoint is not started. Shutting down.");
			}

			LOG.info("Shutdown");
		}
	}

	public void init() {
		new Thread(new AsyncConsumer()).start();
		LOG.info("Message Consumer started");
	}

	public void destroy() {
		shutdown = true;
	}
}
