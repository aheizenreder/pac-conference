/**
 * 
 */
package com.prodyna.pac.conference.rest.admin.talk;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.rest.RestUnknowExceptionHandler;
import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.talk.model.Talk;
import com.prodyna.pac.conference.talk.service.TalkService;

/**
 * Implementation of REST interface for talk service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@RequestScoped
@Path("admin/talk")
public class TalkAdminRestServiceImpl implements TalkAdminRestService {

	@Inject
	private TalkService talkService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.talk.TalkAdminRestService#create
	 * (com.prodyna.pac.conference.talk.model.Talk)
	 */
	@Override
	public Response create(Talk talk) {
		Response.ResponseBuilder builder;

		try {
			talk = talkService.create(talk);
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
	 * com.prodyna.pac.conference.rest.admin.talk.TalkAdminRestService#update
	 * (com.prodyna.pac.conference.talk.model.Talk)
	 */
	@Override
	public Response update(Talk talk) {
		Response.ResponseBuilder builder;

		try {
			Talk updatedTalk = talkService.update(talk);
			builder = Response.ok(updatedTalk);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.talk.TalkAdminRestService#delete
	 * (long)
	 */
	@Override
	public Response delete(long id) {
		Response.ResponseBuilder builder;

		try {
			Talk talk = talkService.get(id);
			Talk deletedTalk = talkService.delete(talk);
			builder = Response.ok(deletedTalk);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.talk.TalkAdminRestService#assignRoom
	 * (com.prodyna.pac.conference.talk.model.Talk,
	 * com.prodyna.pac.conference.location.model.Room)
	 */
	@Override
	public Response assignRoom(Talk talk, Room room) {
		Response.ResponseBuilder builder;

		try {
			boolean result = talkService.assignRoom(talk, room);
			builder = Response.ok(result);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.talk.TalkAdminRestService#unassignRoom
	 * (com.prodyna.pac.conference.talk.model.Talk,
	 * com.prodyna.pac.conference.location.model.Room)
	 */
	@Override
	public Response unassignRoom(Talk talk, Room room) {
		Response.ResponseBuilder builder;

		try {
			boolean result = talkService.unassignRoom(talk, room);
			builder = Response.ok(result);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.talk.TalkAdminRestService#assignSpeaker
	 * (com.prodyna.pac.conference.talk.model.Talk,
	 * com.prodyna.pac.conference.speaker.model.Speaker)
	 */
	@Override
	public Response assignSpeaker(Talk talk, Speaker speaker) {
		Response.ResponseBuilder builder;

		try {
			boolean result = talkService.assignSpeaker(talk, speaker);
			builder = Response.ok(result);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.rest.admin.talk.TalkAdminRestService#
	 * unassignSpeaker(com.prodyna.pac.conference.talk.model.Talk,
	 * com.prodyna.pac.conference.speaker.model.Speaker)
	 */
	@Override
	public Response unassignSpeaker(Talk talk, Speaker speaker) {
		Response.ResponseBuilder builder;

		try {
			boolean result = talkService.unassignSpeaker(talk, speaker);
			builder = Response.ok(result);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

}
