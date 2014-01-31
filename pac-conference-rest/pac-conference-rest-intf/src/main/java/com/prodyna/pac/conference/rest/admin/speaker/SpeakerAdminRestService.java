/**
 * 
 */
package com.prodyna.pac.conference.rest.admin.speaker;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.speaker.model.Speaker;

/**
 * Administration REST interface for speaker service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface SpeakerAdminRestService {

	/**
	 * create new speaker.
	 * 
	 * @param speaker
	 *            speaker data to create new speaker.
	 * @return the representation of created speaker wrapped in a reponse
	 *         object.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Speaker speaker);

	/**
	 * updates a speaker.
	 * 
	 * @param speaker
	 *            speaker object with new values for update.
	 * 
	 * @return updated representation of the speaker after update wrapped in
	 *         a response object.
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Speaker speaker);

	/**
	 * deletes a speaker identified by its id.
	 * 
	 * @param id
	 *            Id of a speaker to delete.
	 * @return deleted speaker wrapped in a response object.
	 */
	@DELETE
	@Path("/{id:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id);

}
