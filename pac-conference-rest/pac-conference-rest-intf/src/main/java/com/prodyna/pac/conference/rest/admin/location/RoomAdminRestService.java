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

import com.prodyna.pac.conference.location.model.Room;

/**
 * Administration REST interface for room service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface RoomAdminRestService {
	/**
	 * create new room.
	 * 
	 * @param room
	 *            room data to create new room.
	 * @return the representation of created room wrapped in a reponse object.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Room room);

	/**
	 * updates a room.
	 * 
	 * @param room
	 *            room object with new values for update.
	 * 
	 * @return updated representation of the room after update wrapped in a
	 *         response object.
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Room room);

	/**
	 * deletes a room identified by its id.
	 * 
	 * @param id
	 *            Id of a room to delete.
	 * @return deleted room wrapped in a response object.
	 */
	@DELETE
	@Path("/{id:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id);

}
