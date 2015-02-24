package com.epam.console.messaging.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.Logger;
import com.epam.console.messaging.MessagingEndpoint;

public abstract class AbstractSocketEndpoint<T> implements MessagingEndpoint<T> {

	private static final Logger LOG = Logger.getLogger(AbstractSocketEndpoint.class);

	private int port;
	private ServerSocket serverSocket;
	private Socket socket;
	private InputStream is;
	private ObjectInputStream ois;

	public AbstractSocketEndpoint(int port) throws IOException {
		this.port = port;
		init();
		LOG.info("Init of Socket Endpoint completed");
	}

	public void reconnect() throws IOException {
		init();
	}

	private void init() throws IOException {
		LOG.info("Connecting to socket [port=" + port + "]");
		serverSocket = new ServerSocket(port);
		socket = serverSocket.accept();
		is = new BufferedInputStream(socket.getInputStream());
		ois = new ObjectInputStream(is);

		LOG.info("Successfully connected!");
	}

	public void disconnect() throws IOException {
		LOG.info("Disconnecting from Socket [port=" + port + "]");
		if (ois != null) {
			ois.close();
		}
		if (is != null) {
			is.close();
		}
		if (socket != null) {
			socket.close();
		}
		if (serverSocket != null) {
			serverSocket.close();
		}
	}

	public ObjectInputStream getObjectInputStream() {
		return ois;
	}

	public abstract T readMessage() throws ClassNotFoundException, IOException, InterruptedException;
}
