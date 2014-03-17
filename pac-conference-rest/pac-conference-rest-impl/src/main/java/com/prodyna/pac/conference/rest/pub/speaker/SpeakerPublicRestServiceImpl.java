/**
 * 
 */
package com.prodyna.pac.conference.rest.pub.speaker;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.rest.RestUnknowExceptionHandler;
import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.speaker.service.SpeakerService;

/**
 * Implementation of public REST interface for speaker service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@RequestScoped
@Path("{access:(admin|public)}/speaker")
public class SpeakerPublicRestServiceImpl implements SpeakerPublicRestService {

	@Inject
	private SpeakerService speakerService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.pub.speaker.SpeakerPublicRestService#
	 * getById(long)
	 */
	@Override
	public Response getById(long id) {
		Response.ResponseBuilder builder = null;

		try {
			Speaker speaker = speakerService.get(id);
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
	 * com.prodyna.pac.conference.rest.pub.speaker.SpeakerPublicRestService#
	 * getAll()
	 */
	@Override
	public Response getAll() {
		Response.ResponseBuilder builder;

		try {
			List<Speaker> allSpeaker = speakerService.getAll();
			builder = Response.ok(allSpeaker);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}
}
