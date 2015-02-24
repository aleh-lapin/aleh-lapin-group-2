package com.epam.console.messaging;

import java.io.IOException;

public interface MessagingEndpoint<T> {

	void reconnect() throws IOException;

	void disconnect() throws IOException;

	T readMessage() throws ClassNotFoundException, IOException, InterruptedException;
}
