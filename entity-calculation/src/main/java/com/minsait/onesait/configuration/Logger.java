package com.minsait.onesait.configuration;

import java.net.InetSocketAddress;

import org.graylog2.gelfclient.GelfConfiguration;
import org.graylog2.gelfclient.GelfMessage;
import org.graylog2.gelfclient.GelfMessageBuilder;
import org.graylog2.gelfclient.GelfMessageLevel;
import org.graylog2.gelfclient.GelfTransports;
import org.graylog2.gelfclient.transport.GelfTransport;

import com.fnproject.fn.api.RuntimeContext;

public class Logger {

	private static Logger LOGGER;
	private static GelfTransport TRANSPORT;

	public static void initialize(RuntimeContext ctx) {
		setUpLogger(ctx);
	}

	private static void setUpLogger(RuntimeContext ctx) {
		final String grayLogHost = ctx.getConfigurationByKey("GRAYLOG_HOST").orElse(null);
		final String grayLogPort = ctx.getConfigurationByKey("GRAYLOG_PORT").orElse(null);
		if (grayLogHost != null && grayLogPort != null) {
			final GelfConfiguration config = new GelfConfiguration(
					new InetSocketAddress(grayLogHost, Integer.valueOf(grayLogPort))).transport(GelfTransports.UDP)
							.queueSize(512).connectTimeout(5000).reconnectDelay(1000).tcpNoDelay(true)
							.sendBufferSize(32768);
			System.out.print("Initializing logger");
			TRANSPORT = GelfTransports.create(config);
		}
	}

	public synchronized static Logger getInstance() {
		if (LOGGER == null) {
			LOGGER = new Logger();
		}
		return LOGGER;
	}

	public void info(String msg) {
		sendLogMessage(msg, GelfMessageLevel.INFO);
	}

	public void debug(String msg) {
		sendLogMessage(msg, GelfMessageLevel.DEBUG);
	}

	public void error(String msg) {
		sendLogMessage(msg, GelfMessageLevel.ERROR);
	}

	private void sendLogMessage(String msg, GelfMessageLevel level) {
		final GelfMessageBuilder builder = new GelfMessageBuilder(msg).level(level).additionalField("app_name",
				"entityCalculationFn");
		final GelfMessage message = builder.message(msg).build();
		try {
			TRANSPORT.send(message);
			System.out.print("Message sent");
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

}
