package com.xzymon.nirvanasubscriber.jms;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Slf4j
@Component
@NoArgsConstructor
public class LoggingErrorHandler implements ErrorHandler {

	@Override
	public void handleError(Throwable t) {
		log.error("JMS Message error occurred!", t);
	}
}
