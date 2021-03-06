/**
 * 
 */
package com.prodyna.pac.conference.rest.pub.conference;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Public REST interface for conference service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface ConferencePublicRestService {

	/**
	 * get conference by its id.
	 * 
	 * @param id
	 *            id of conference to be returned.
	 * @return instance of conference for requested id wrapped with HTTP
	 *         response information in a response object.
	 */
	@GET
	@Path("/{id:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") long id);

	/**
	 * get all conferences in a list.
	 * 
	 * @return all conferences in a list wrapped in a response object with HTTP
	 *         response informations.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll();
}
