/**
 * 
 */
package com.prodyna.pac.conference.rest.pub.talk;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.rest.RestUnknowExceptionHandler;
import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.talk.model.Talk;
import com.prodyna.pac.conference.talk.service.TalkService;

/**
 * Implementation of public REST interface for talk service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@RequestScoped
@Path("{access:(admin|public)}/talk")
public class TalkPublicRestServiceImpl implements TalkPublicRestService {

	@Inject
	private TalkService talkService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.pub.talk.TalkPublicRestService#getById
	 * (long)
	 */
	@Override
	public Response getById(long id) {

		Response.ResponseBuilder builder = null;

		try {
			Talk talk = talkService.get(id);
			builder = Response.ok(talk);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}

		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.pub.talk.TalkPublicRestService#getAll()
	 */
	@Override
	public Response getAll() {
		Response.ResponseBuilder builder;

		try {
			List<Talk> allTalkList = talkService.getAll();
			builder = Response.ok(allTalkList);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.rest.pub.talk.TalkPublicRestService#
	 * findTalksByConference(long)
	 */
	@Override
	public Response findTalksByConference(Conference conference) {
		Response.ResponseBuilder builder;

		try {
			List<Talk> talksByConferenceList = talkService
					.findTalksByConference(conference);

			builder = Response.ok(talksByConferenceList);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.rest.pub.talk.TalkPublicRestService#
	 * findSpeakerByTalk(long)
	 */
	@Override
	public Response findSpeakerByTalk(Talk talk) {
		Response.ResponseBuilder builder;

		try {
			List<Speaker> speakerByTalkList = talkService
					.findSpeakerByTalk(talk);
			builder = Response.ok(speakerByTalkList);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.rest.pub.talk.TalkPublicRestService#
	 * findTalksByRoom(long)
	 */
	@Override
	public Response findTalksByRoom(Room room) {
		Response.ResponseBuilder builder;

		try {
			List<Talk> talksByRoomList = talkService.findTalksByRoom(room);
			builder = Response.ok(talksByRoomList);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.pub.talk.TalkPublicRestService#findRoomByTalk
	 * (long)
	 */
	@Override
	public Response findRoomByTalk(Talk talk) {
		Response.ResponseBuilder builder;

		try {
			Room room = talkService.findRoomByTalk(talk);
			builder = Response.ok(room);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

}
