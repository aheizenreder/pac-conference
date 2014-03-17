/**
 * 
 */
package com.prodyna.pac.conference.rest.pub.location;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.location.model.Location;
import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.location.service.LocationService;
import com.prodyna.pac.conference.location.service.RoomService;
import com.prodyna.pac.conference.rest.RestUnknowExceptionHandler;

/**
 * Implementation of public REST interface for room service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@RequestScoped
@Path("{access:(admin|public)}/room")
public class RoomPublicRestServiceImpl implements RoomPublicRestService {

	@Inject
	private RoomService roomService;

	@Inject
	private LocationService locationService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.rest.pub.location.RoomPublicRestService#getById
	 * (long)
	 */
	@Override
	public Response getById(long id) {
		Response.ResponseBuilder builder;

		try {
			Room room = roomService.get(id);
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
	 * com.prodyna.pac.conference.rest.pub.location.RoomPublicRestService#getAll
	 * ()
	 */
	@Override
	public Response getAll() {
		Response.ResponseBuilder builder;

		try {
			List<Room> allRooms = roomService.getAll();
			builder = Response.ok(allRooms);
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.rest.pub.location.RoomPublicRestService#
	 * getByLocation(long)
	 */
	@Override
	public Response getByLocation(@QueryParam("locationId") long locationId) {
		Response.ResponseBuilder builder;

		try {
			if (locationId != 0) {
				Location location = locationService.get(locationId);
				List<Room> roomsByLocation = roomService
						.findRoomByLocation(location);
				builder = Response.ok(roomsByLocation);
			} else {
				// if locationId is null or 0 then call getAll()
				return getAll();
			}
		} catch (Exception e) {
			builder = RestUnknowExceptionHandler.handleUnknowException(e);
		}
		return builder.build();
	}

}
