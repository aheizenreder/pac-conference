/**
 * 
 */
package com.prodyna.pac.conference.rest.admin.conference;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.conference.service.ConferenceService;
import com.prodyna.pac.conference.rest.RestUnknowExceptionHandler;
import com.prodyna.pac.conference.rest.admin.conference.ConferenceAdminRestService;

/**
 * Implementation of REST interface for conference service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@RequestScoped
@Path("admin/conference")
public class ConferenceAdminRestServiceImpl implements
		ConferenceAdminRestService {

	@Inject
	private ConferenceService conferenceService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.conference.ConferenceAdminRestService
	 * #create(com.prodyna.pac.conference.conference.model.Conference)
	 */
	@Override
	public Response create(Conference conference) {
		Response.ResponseBuilder builder;

		try {
			conference = conferenceService.create(conference);
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
	 * com.prodyna.pac.conference.rest.admin.conference.ConferenceAdminRestService
	 * #update(com.prodyna.pac.conference.conference.model.Conference)
	 */
	@Override
	public Response update(Conference conference) {
		Response.ResponseBuilder builder;

		try {
			Conference updatedConference = conferenceService.update(conference);
			builder = Response.ok(updatedConference);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.conference.ConferenceAdminRestService
	 * #delete(long)
	 */
	@Override
	public Response delete(long id) {
		Response.ResponseBuilder builder;

		try {
			Conference conference = conferenceService.get(id);
			conference = conferenceService.delete(conference);
			builder = Response.ok(conference);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

}
