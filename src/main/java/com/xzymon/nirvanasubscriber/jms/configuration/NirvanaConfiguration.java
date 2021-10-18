package com.xzymon.nirvanasubscriber.jms.configuration;

import com.pcbsys.nirvana.nJMS.QueueConnectionFactoryImpl;
import com.xzymon.nirvanasubscriber.jms.LoggingErrorHandler;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.util.ErrorHandler;

import javax.jms.ConnectionFactory;

@Configuration
@NoArgsConstructor
public class NirvanaConfiguration {

	@Value("${um.jms.nirvana.provider.url}")
	private String umJmsNirvanaProviderUrl;

	@Value("${um.jms.nirvana.security.principal}")
	private String umJmsUsername;

	@Value("${um.jms.nirvana.security.credentials}")
	private String umJmsCredentials;

	@Value("${um.jms.nirvana.max-redelivery}")
	private int maxRedelivery;

	@Bean
	public ConnectionFactory nirvanaJmsConnectionFactory() {
		final QueueConnectionFactoryImpl connectionFactory = new QueueConnectionFactoryImpl(umJmsNirvanaProviderUrl);
		connectionFactory.setRNAME(umJmsNirvanaProviderUrl);
		connectionFactory.setUseJMSEngine(true);
		connectionFactory.setAutoCreateResource(false);
		connectionFactory.setMaxRedelivery(maxRedelivery);

		final UserCredentialsConnectionFactoryAdapter credentialsCFAdapter = new UserCredentialsConnectionFactoryAdapter();
		credentialsCFAdapter.setTargetConnectionFactory(connectionFactory);
		credentialsCFAdapter.setUsername(umJmsUsername);
		credentialsCFAdapter.setPassword(umJmsCredentials);

		return credentialsCFAdapter;
	}

	@Bean
	public JmsListenerContainerFactory nirvanaContainerFactory(final ConnectionFactory connectionFactory,
	                                                           final DefaultJmsListenerContainerFactoryConfigurer configurer,
	                                                           final LoggingErrorHandler errorHandler) {
		final DefaultJmsListenerContainerFactory listenerContainerFactory = new DefaultJmsListenerContainerFactory();
		listenerContainerFactory.setErrorHandler(errorHandler);
		configurer.configure(listenerContainerFactory, connectionFactory);
		return listenerContainerFactory;
	}

}
