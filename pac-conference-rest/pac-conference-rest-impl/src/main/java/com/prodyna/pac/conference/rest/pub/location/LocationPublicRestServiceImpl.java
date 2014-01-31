/**
 * 
 */
package com.prodyna.pac.conference.rest.pub.location;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.location.model.Location;
import com.prodyna.pac.conference.location.service.LocationService;
import com.prodyna.pac.conference.rest.RestUnknowExceptionHandler;

/**
 * Implementation for public rest interface for location service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@RequestScoped
@Path("public/location")
public class LocationPublicRestServiceImpl implements LocationPublicRestService {

	@Inject
	private LocationService locationService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.pub.location.LocationPublicRestService
	 * #getById(long)
	 */
	@Override
	public Response getById(long id) {

		Response.ResponseBuilder builder = null;

		try {
			Location location = locationService.get(id);
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
	 * com.prodyna.pac.conference.rest.pub.location.LocationPublicRestService
	 * #getAll()
	 */
	@Override
	public Response getAll() {
		Response.ResponseBuilder builder;

		try {
			List<Location> allLocation = locationService.getAll();
			builder = Response.ok(allLocation);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

}
