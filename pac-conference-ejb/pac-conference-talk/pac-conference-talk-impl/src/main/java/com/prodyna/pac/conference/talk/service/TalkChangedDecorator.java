/**
 * 
 */
package com.prodyna.pac.conference.talk.service;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.slf4j.Logger;

import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.talk.model.Talk;
import com.prodyna.pac.conference.talk.service.exception.OccupiedRoomException;
import com.prodyna.pac.conference.talk.service.exception.SpeakerNotAvailableException;
import com.prodyna.pac.conference.talk.service.exception.TalkOutOfConferenceDateBoundsException;
import com.prodyna.pac.conference.talk.service.exception.WrongLocationException;

/**
 * Decorator for talk changes notifications.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@Decorator
public abstract class TalkChangedDecorator implements TalkService {

	private static final String TALK_CHANGED_NOTIFICATION_QUEUE_NAME = "queue/talkChangedNotificationQueue";

	@Inject
	private Logger log;

	/**
	 * talk service to delegate requests to.
	 */
	@Inject
	@Delegate
	@Any
	private TalkService talkService;

	@Inject
	private InitialContext ctx;

	@Inject
	private QueueConnectionFactory qcf;

	/**
	 * 
	 */
	public TalkChangedDecorator() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#create(com.prodyna
	 * .pac.conference.talk.model.Talk)
	 */
	@Override
	public Talk create(Talk talk) throws TalkOutOfConferenceDateBoundsException {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("A new talk '");
		stringBuffer.append(talk.getTitle());
		stringBuffer.append("' is added to conference program.");
		sendNotificationMessage(stringBuffer.toString());
		return talkService.create(talk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#update(com.prodyna
	 * .pac.conference.talk.model.Talk)
	 */
	@Override
	public Talk update(Talk talk) throws TalkOutOfConferenceDateBoundsException {

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("There are updates on talk '");
		stringBuffer.append(talk.getTitle());
		stringBuffer.append("'.");

		sendNotificationMessage(stringBuffer.toString());

		return talkService.update(talk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#delete(com.prodyna
	 * .pac.conference.talk.model.Talk)
	 */
	@Override
	public Talk delete(Talk talk) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("The talk '");
		stringBuffer.append(talk.getTitle());
		stringBuffer.append("' is not more on program.");

		sendNotificationMessage(stringBuffer.toString());

		return talkService.delete(talk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#assignRoom(com.prodyna
	 * .pac.conference.talk.model.Talk,
	 * com.prodyna.pac.conference.location.model.Room)
	 */
	@Override
	public boolean assignRoom(Talk talk, Room room)
			throws WrongLocationException, OccupiedRoomException {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("The talk '");
		stringBuffer.append(talk.getTitle());
		stringBuffer.append("' is now in the room '");
		stringBuffer.append(room.getName());
		stringBuffer.append("'.");

		return talkService.assignRoom(talk, room);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#assignSpeaker(com
	 * .prodyna.pac.conference.talk.model.Talk,
	 * com.prodyna.pac.conference.speaker.model.Speaker)
	 */
	@Override
	public boolean assignSpeaker(Talk talk, Speaker speaker)
			throws SpeakerNotAvailableException {

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("There are updates on talk '");
		stringBuffer.append(talk.getTitle());
		stringBuffer.append("'.");

		return talkService.assignSpeaker(talk, speaker);
	}

	private void sendNotificationMessage(String message) {
		try {
			Queue q = (Queue) ctx.lookup(TALK_CHANGED_NOTIFICATION_QUEUE_NAME);
			Connection connection = qcf.createConnection();
			connection.start();
			Session s = connection
					.createSession(true, Session.AUTO_ACKNOWLEDGE);
			MessageProducer mp = s.createProducer(q);
			TextMessage m = s.createTextMessage(message);
			mp.send(m);
			log.info("Message sent: '" + message + "'");
			mp.close();
			s.commit();
			s.close();
			connection.stop();
			connection.close();
		} catch (Exception e) {
			log.error("Error by sendig message on Queue: "
					+ e.getLocalizedMessage());
		}

	}
}
