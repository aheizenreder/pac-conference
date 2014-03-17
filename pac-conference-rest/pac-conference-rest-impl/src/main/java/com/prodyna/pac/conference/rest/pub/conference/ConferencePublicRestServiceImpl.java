/**
 * 
 */
package com.prodyna.pac.conference.rest.pub.conference;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.conference.service.ConferenceService;
import com.prodyna.pac.conference.rest.RestUnknowExceptionHandler;

/**
 * Implementation of public REST interface for conference service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 *
 */
@RequestScoped
@Path("{access:(admin|public)}/conference")
public class ConferencePublicRestServiceImpl implements
		ConferencePublicRestService {

	@Inject
	private ConferenceService conferenceService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.pub.conference.ConferencePublicRestService
	 * #getById(long)
	 */
	@Override
	public Response getById(long id) {
		Response.ResponseBuilder builder;

		try {
			Conference conference = conferenceService.get(id);
			builder = Response.ok(conference);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}

		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.pub.conference.ConferencePublicRestService
	 * #getAll()
	 */
	@Override
	public Response getAll() {

		Response.ResponseBuilder builder;

		try {
			List<Conference> allConferences = conferenceService.getAll();
			builder = Response.ok(allConferences);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

}
