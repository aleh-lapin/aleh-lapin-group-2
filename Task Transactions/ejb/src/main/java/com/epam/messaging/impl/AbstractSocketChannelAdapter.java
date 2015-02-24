package com.epam.messaging.impl;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.epam.messaging.common.ChannelAdapter;

public abstract class AbstractSocketChannelAdapter {

	private static final Logger LOG = Logger.getLogger(ChannelAdapter.class);
	private static final String HOST = "127.0.0.1";
	private int port;
	private Socket socket;
	private OutputStream os;
	private ObjectOutputStream oos;

	public AbstractSocketChannelAdapter(int port) throws IOException {
		this.port = port;
	}

	public void connectToChannel() throws IOException {
		LOG.info("Connecting to socket [port=" + port + "]");
		socket = new Socket(HOST, port);
		os = new BufferedOutputStream(socket.getOutputStream());
		oos = new ObjectOutputStream(os);
		LOG.info("Successfully connected to Socket [" + HOST + ":" + port + "]");
	}

	public void diconectFromChannel() throws IOException {
		LOG.info("Disconnecting from Socket [port=" + port + "]");

		if (oos != null) {
			oos.close();
		}
		if (os != null) {
			os.close();
		}
		if (socket != null) {
			socket.close();
		}
	}

	public void reconnectToChannel() throws IOException {
		diconectFromChannel();
		connectToChannel();

		if (socket != null && os != null && oos != null) {
			LOG.info("Reconect to Socket Channel was successfull");
		} else {
			LOG.warn("Reconnect Attempt failed");
		}
	}

	public ObjectOutputStream getObjectOutputStream() {
		return oos;
	}
}
