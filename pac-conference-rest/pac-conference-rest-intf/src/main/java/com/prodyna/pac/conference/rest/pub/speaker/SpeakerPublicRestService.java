/**
 * 
 */
package com.prodyna.pac.conference.rest.pub.speaker;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Public REST interface for speaker service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface SpeakerPublicRestService {

	/**
	 * get speaker by its id.
	 * 
	 * @param id
	 *            id of speaker to be returned.
	 * @return instance of speaker for requested id wrapped with HTTP response
	 *         information in a response object.
	 */
	@GET
	@Path("/{id:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") long id);

	/**
	 * get all speakers in a list.
	 * 
	 * @return all speaker in a list wrapped in a response object with HTTP
	 *         response informations.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll();

}
