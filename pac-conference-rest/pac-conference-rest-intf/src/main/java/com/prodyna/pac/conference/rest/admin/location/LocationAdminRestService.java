/**
 * 
 */
package com.prodyna.pac.conference.rest.admin.location;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.location.model.Location;

/**
 * Administration REST interface for location service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface LocationAdminRestService {

	/**
	 * create new location.
	 * 
	 * @param location
	 *            location data to create new location.
	 * @return the representation of created location wrapped in a reponse
	 *         object.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Location location);

	/**
	 * updates a location.
	 * 
	 * @param location
	 *            location object with new values for update.
	 * 
	 * @return updated representation of the location after update wrapped in a
	 *         response object.
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Location location);

	/**
	 * deletes a location identified by its id.
	 * 
	 * @param id
	 *            Id of a location to delete.
	 * @return deleted location wrapped in a response object.
	 */
	@DELETE
	@Path("/{id:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id);

}
