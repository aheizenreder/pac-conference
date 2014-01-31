/**
 * 
 */
package com.prodyna.pac.conference.rest.pub.location;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Public REST interface for room service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface RoomPublicRestService {
	/**
	 * get room by its id.
	 * 
	 * @param id
	 *            id of room to be returned.
	 * @return instance of room for requested id wrapped with HTTP response
	 *         information in a response object.
	 */
	@GET
	@Path("/{id:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") long id);

	/**
	 * get all rooms in a list.
	 * 
	 * @return all rooms in a list wrapped in a response object with HTTP
	 *         response informations.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll();

	/**
	 * get all rooms in a location identified by location id.
	 * 
	 * @param locationId
	 *            id of location which rooms to return.
	 * @return a list with room for location.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByLocation(@QueryParam("locationId") long locationId);

}
