package com.epam.console.messaging.control;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.epam.console.messaging.MessageConsumer;
import com.epam.console.messaging.MessagingEndpoint;
import com.epam.console.messaging.impl.MasterSocketEndpoint;
import com.epam.console.messaging.impl.SocketMessagingEndpointImpl;
import com.epam.shared.ServerState;

public class ControlBus {

	private static final Logger LOG = Logger.getLogger(ControlBus.class);

	private static final int MASTER_PORT = 3777;
	private static final int MESSAGING_PORT = 3778;
	private static MessagingEndpoint<ServerState> endpoint;
	private static MessageConsumer consumer;
	private static boolean shutdown = false;

	private static final class AsyncMessageConsumer implements Runnable {

		@Override
		public void run() {
			try {
				endpoint = new MasterSocketEndpoint(MASTER_PORT);
			} catch (IOException e) {
				LOG.error("Error while starting Control Bus Messaging Endpoint");
			}


			if (endpoint != null) {
				while (!shutdown) {
					try {
						ServerState state = endpoint.readMessage();

						if (state != null) {
							LOG.info("Server state is changed: " + state);

							if (state.equals(ServerState.STARTUP) && consumer == null) {
								consumer = new MessageConsumer(
										new SocketMessagingEndpointImpl(MESSAGING_PORT));
								consumer.init();
							} else if (state.equals(ServerState.SHUTDOWN) && consumer != null) {
								consumer.destroy();
							}
						} else {
							LOG.warn("NULL state received");
						}
					} catch (ClassNotFoundException | IOException
							| InterruptedException e) {
						LOG.error("Exception while reading state: " + e.fillInStackTrace());
					}
				}

				try {
					endpoint.disconnect();
				} catch (IOException e) {
					LOG.error("Error while shutting down Control Bus endpoint: " + e.fillInStackTrace());
				}
			} else {
				LOG.error("Control Bus Enpoint is not started. Shutting down.");
			}

			LOG.info("Shutdown");
		}
	}

	public void startup() {
		LOG.info("Starting Control BUS");
		new Thread(new AsyncMessageConsumer()).start();
	}

	public void shutdown() {
		shutdown = true;
	}
}
