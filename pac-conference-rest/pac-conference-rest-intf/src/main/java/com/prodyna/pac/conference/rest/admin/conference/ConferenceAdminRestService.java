/**
 * 
 */
package com.prodyna.pac.conference.rest.admin.conference;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.conference.model.Conference;

/**
 * Administration REST interface for conference service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface ConferenceAdminRestService {

	/**
	 * create new conference.
	 * 
	 * @param conference
	 *            conference data to create new conference.
	 * @return the representation of created conference wrapped in a reponse
	 *         object.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Conference conference);

	/**
	 * updates a conference.
	 * 
	 * @param conference
	 *            conference object with new values for update.
	 * 
	 * @return updated representation of the conference after update wrapped in
	 *         a response object.
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Conference conference);

	/**
	 * deletes a conference identified by its id.
	 * 
	 * @param id
	 *            Id of a conference to delete.
	 * @return deleted conference wrapped in a response object.
	 */
	@DELETE
	@Path("/{id:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id);
}
