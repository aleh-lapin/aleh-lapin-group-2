package com.epam.console.messaging.impl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.epam.shared.OrderEventVO;

public class SocketMessagingEndpointImpl extends AbstractSocketEndpoint<OrderEventVO> {

	private static final Logger LOG = Logger.getLogger(SocketMessagingEndpointImpl.class);

	public SocketMessagingEndpointImpl(int port) throws IOException {
		super(port);
	}

	@Override
	public OrderEventVO readMessage() throws ClassNotFoundException, IOException, InterruptedException {
		OrderEventVO event = null;

		Object object = getObjectInputStream().readObject();
		if (object instanceof OrderEventVO) {
			event = (OrderEventVO) object;
		} else {
			LOG.info("Unknown Event received");
		}

		return event;
	}
}
