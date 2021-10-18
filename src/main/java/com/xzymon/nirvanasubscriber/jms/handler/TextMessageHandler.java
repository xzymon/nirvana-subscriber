package com.xzymon.nirvanasubscriber.jms.handler;

import com.xzymon.nirvanasubscriber.domain.TextMessageContent;
import com.xzymon.nirvanasubscriber.repository.TextMessageContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TextMessageHandler {

	private final TextMessageContentRepository textMessageContentRepository;

	public void handleMessage(final TextMessage message) throws JMSException {
		log.info("Handling message");
		TextMessageContent tmc = new TextMessageContent();
		tmc.setContent(message.getText());
		tmc.setOccuredTime(new Date());
		textMessageContentRepository.save(tmc);
		log.info("Message saved");
	}
}
