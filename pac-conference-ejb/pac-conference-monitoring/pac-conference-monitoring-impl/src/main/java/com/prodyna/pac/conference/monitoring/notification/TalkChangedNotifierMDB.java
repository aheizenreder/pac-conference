package com.prodyna.pac.conference.monitoring.notification;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;

/**
 * Message driven bean to process notifications for talk changes.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public @MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/talkChangedNotificationQueue") })
class TalkChangedNotifierMDB implements MessageListener {

	@Inject
	private Logger log;

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = null;
		try {
			if (message instanceof TextMessage) {
				textMessage = (TextMessage) message;
				log.info("Received Talk changed notification: "
						+ textMessage.getText());
			} else {
				log.error("Not supported message type received: "
						+ message.getClass().getName());
			}
		} catch (JMSException e) {
			log.error("Error while receiving notification message: " + e);
		}
	}

}
