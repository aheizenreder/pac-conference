/**
 * 
 */
package com.prodyna.pac.conference.rest.admin.speaker;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.rest.RestUnknowExceptionHandler;
import com.prodyna.pac.conference.rest.admin.speaker.SpeakerAdminRestService;
import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.speaker.service.SpeakerService;

/**
 * Implementation of REST interface for speaker service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@RequestScoped
@Path("admin/speaker")
public class SpeakerAdminRestServiceImpl implements SpeakerAdminRestService {

	@Inject
	private SpeakerService speakerService;


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.speaker.SpeakerAdminRestService
	 * #create(com.prodyna.pac.conference.speaker.model.Speaker)
	 */
	@Override
	public Response create(Speaker speaker) {
		Response.ResponseBuilder builder;

		try {
			speaker = speakerService.create(speaker);
			builder = Response.ok(speaker);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.speaker.SpeakerAdminRestService
	 * #update(com.prodyna.pac.conference.speaker.model.Speaker)
	 */
	@Override
	public Response update(Speaker speaker) {
		Response.ResponseBuilder builder;

		try {
			Speaker updatedSpeaker = speakerService.update(speaker);
			builder = Response.ok(updatedSpeaker);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.speaker.SpeakerAdminRestService
	 * #delete(long)
	 */
	@Override
	public Response delete(long id) {
		Response.ResponseBuilder builder;

		try {
			Speaker speaker = speakerService.get(id);
			speaker = speakerService.delete(speaker);
			builder = Response.ok(speaker);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

}
