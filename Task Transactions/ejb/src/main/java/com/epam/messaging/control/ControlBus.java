package com.epam.messaging.control;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.log4j.Logger;

import com.epam.messaging.common.ChannelAdapter;
import com.epam.messaging.common.OrderStateSender;
import com.epam.messaging.impl.MasterSocketChannelAdapter;
import com.epam.messaging.impl.SocketChannelAdapterImpl;
import com.epam.shared.ServerState;

@Singleton
@Startup
public class ControlBus {

	private static final String INIT_ERROR_MESSAGE = "ERROR occured on init of ControlBus: ";
	private static final Logger LOG = Logger.getLogger(ControlBus.class);
	private static final int STARTUP_DELAY = 100;
	private static final int MESSAGING_DELAY = 50;
	private static final int MASTER_PORT = 3777;
	private static final int MESSAGING_PORT = 3778;
	private static ChannelAdapter<ServerState> masterChannel;
	private static OrderStateSender sender;

	private static final class AsyncControl implements Runnable {

		public void run() {
			try {
				Thread.sleep(STARTUP_DELAY);

				masterChannel = new MasterSocketChannelAdapter(MASTER_PORT);
				masterChannel.publishMessage(ServerState.STARTUP);

				Thread.sleep(MESSAGING_DELAY);

				sender = OrderStateSender.getInstance(new SocketChannelAdapterImpl(MESSAGING_PORT));
				sender.startSending();
			} catch (InterruptedException e) {
				LOG.error(INIT_ERROR_MESSAGE + e.fillInStackTrace());
			} catch (IOException e) {
				LOG.error(INIT_ERROR_MESSAGE + e.fillInStackTrace());
			}
		}
	}

	@PostConstruct
	private void startup() {
		LOG.info("Startin Control BUS");
		new Thread(new AsyncControl()).start();
	}

	@PreDestroy
	private void shutdown() {
		try {
			if (sender != null) {
				sender.shutdown();
			}

			if (masterChannel != null) {
				masterChannel.publishMessage(ServerState.SHUTDOWN);
			}
		} catch (IOException e) {
			LOG.error("ERROR occured on shutdown of ControlBus" + e.fillInStackTrace());
		}

	}
}
