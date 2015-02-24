package com.epam.messaging.common;

import java.io.IOException;

public interface ChannelAdapter<T> {

	void connectToChannel() throws IOException;

	void diconectFromChannel() throws IOException;

	void reconnectToChannel() throws IOException;

	void publishMessage(T event) throws IOException;

}
