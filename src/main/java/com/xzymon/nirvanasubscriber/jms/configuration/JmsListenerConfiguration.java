package com.xzymon.nirvanasubscriber.jms.configuration;

import com.xzymon.nirvanasubscriber.jms.handler.TextMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class JmsListenerConfiguration {
	private static final String NIRVANA_CONTAINER_FACTORY = "nirvanaContainerFactory";

	private static JmsListenerEndpointRegistry jmsListenerEndpointRegistry;

	private final TextMessageHandler textMessageHandler;

	@JmsListener(
			destination = "${jms.queue.named}",
			containerFactory = NIRVANA_CONTAINER_FACTORY,
			concurrency = "${jms.queue.named.concurrency}")
	public void receive(final Message message) throws JMSException {
		TextMessage textMessage = (TextMessage) message;
		textMessageHandler.handleMessage(textMessage);
	}

	@Autowired
	public void setJmsListenerEndpointRegistry(final JmsListenerEndpointRegistry registry) {
		this.jmsListenerEndpointRegistry = registry;
	}

	public static void start() {
		jmsListenerEndpointRegistry.start();
		log.info("JMS listeners started");
	}

	public static void stop() {
		jmsListenerEndpointRegistry.stop();
		log.info("JMS listeners stopped");
	}

}
