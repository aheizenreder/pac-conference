/**
 * 
 */
package com.prodyna.pac.conference.rest.admin.location;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.location.model.Location;
import com.prodyna.pac.conference.location.service.LocationService;
import com.prodyna.pac.conference.rest.RestUnknowExceptionHandler;
import com.prodyna.pac.conference.rest.pub.location.LocationPublicRestServiceImpl;

/**
 * Implementation of REST interface for location service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@RequestScoped
@Path("admin/location")
public class LocationAdminRestServiceImpl implements LocationAdminRestService {

	@Inject
	private LocationService locationService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.location.LocationAdminRestService
	 * #create(com.prodyna.pac.conference.location.model.Location)
	 */
	@Override
	public Response create(Location location) {
		Response.ResponseBuilder builder;

		try {
			location = locationService.create(location);
			builder = Response.ok(location);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}

		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.location.LocationAdminRestService
	 * #update(com.prodyna.pac.conference.location.model.Location)
	 */
	@Override
	public Response update(Location location) {
		Response.ResponseBuilder builder;

		try {
			location = locationService.update(location);
			builder = Response.ok(location);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}

		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.location.LocationAdminRestService
	 * #delete(long)
	 */
	@Override
	public Response delete(@PathParam("id") long id) {
		Response.ResponseBuilder builder;

		try {
			Location location = locationService.get(id);
			location = locationService.delete(location);

			builder = Response.ok(location);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}

		return builder.build();
	}

}
