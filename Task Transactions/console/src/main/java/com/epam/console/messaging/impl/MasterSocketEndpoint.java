package com.epam.console.messaging.impl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.epam.shared.ServerState;

public class MasterSocketEndpoint extends AbstractSocketEndpoint<ServerState> {

	private static final Logger LOG = Logger.getLogger(SocketMessagingEndpointImpl.class);

	public MasterSocketEndpoint(int port) throws IOException {
		super(port);
	}

	@Override
	public ServerState readMessage() throws ClassNotFoundException, IOException,
			InterruptedException {
		ServerState event = null;

		Object object = getObjectInputStream().readObject();
		if (object instanceof ServerState) {
			event = (ServerState) object;

			LOG.info("Message received: " + event);
		} else {
			LOG.info("Unknown Event received");
		}

		return event;
	}
}
