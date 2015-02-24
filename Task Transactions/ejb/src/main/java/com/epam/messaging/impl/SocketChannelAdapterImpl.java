package com.epam.messaging.impl;

import java.io.IOException;

import com.epam.messaging.common.ChannelAdapter;
import com.epam.shared.OrderEventVO;

public class SocketChannelAdapterImpl extends AbstractSocketChannelAdapter implements ChannelAdapter<OrderEventVO> {

	public SocketChannelAdapterImpl(int port) throws IOException {
		super(port);
		connectToChannel();
	}

	public void publishMessage(OrderEventVO event) throws IOException {
		getObjectOutputStream().writeObject(event);
		getObjectOutputStream().flush();
	}

}