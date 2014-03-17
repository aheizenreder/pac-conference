/**
 * 
 */
package com.prodyna.pac.conference.rest.admin.location;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.location.model.Location;
import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.location.service.LocationService;
import com.prodyna.pac.conference.location.service.RoomService;
import com.prodyna.pac.conference.rest.RestUnknowExceptionHandler;
import com.prodyna.pac.conference.rest.admin.location.RoomAdminRestService;

/**
 * Implementation of REST interface for room service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@RequestScoped
@Path("admin/room")
public class RoomAdminRestServiceImpl implements RoomAdminRestService {

	@Inject
	private RoomService roomService;
	
	@Inject
	private LocationService locationService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.location.RoomAdminRestService#create
	 * (com.prodyna.pac.conference.location.model.Room)
	 */
	@Override
	public Response create(Room room) {
		Response.ResponseBuilder builder;

		try {
			room = roomService.create(room);
			builder = Response.ok(room);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}

		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.location.RoomAdminRestService#update
	 * (com.prodyna.pac.conference.location.model.Room)
	 */
	@Override
	public Response update(Room room) {
		Response.ResponseBuilder builder;

		try {
			room = roomService.update(room);
			builder = Response.ok(room);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}

		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.admin.location.RoomAdminRestService#delete
	 * (long)
	 */
	@Override
	public Response delete(@PathParam("id") long id) {
		Response.ResponseBuilder builder;

		try {
			Room room = roomService.get(id);
			room = roomService.delete(room);
			builder = Response.ok(room);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}

		return builder.build();
	}

}
