package com.epam.messaging.impl;

import java.io.IOException;

import com.epam.messaging.common.ChannelAdapter;
import com.epam.shared.ServerState;

public class MasterSocketChannelAdapter  extends AbstractSocketChannelAdapter implements ChannelAdapter<ServerState> {

	public MasterSocketChannelAdapter(int port) throws IOException {
		super(port);
		connectToChannel();
	}

	public void publishMessage(ServerState event) throws IOException {
		getObjectOutputStream().writeObject(event);
		getObjectOutputStream().flush();
	}
}