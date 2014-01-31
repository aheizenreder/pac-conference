/**
 * 
 */
package com.prodyna.pac.conference.rest.pub.talk;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Public REST interface for talk service.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface TalkPublicRestService {

	/**
	 * get talk by its id.
	 * 
	 * @param id
	 *            id of talk to be returned.
	 * @return instance of talk for requested id wrapped with HTTP response
	 *         information in a response object.
	 */
	@GET
	@Path("/{id:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") long id);

	/**
	 * get all talks in a list.
	 * 
	 * @return all talks in a list wrapped in a response object with HTTP
	 *         response informations.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll();

	/**
	 * get all talks of a conference identified by conference id.
	 * 
	 * @param conferenceId
	 *            id of conference which talks to return.
	 * @return a list with talks of a conference.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findTalksByConference(
			@QueryParam("conferenceId") long conferenceId);

	/**
	 * get all speakers of a talks identified by talk id.
	 * 
	 * @param talkId
	 *            id of a talk which speakers to return.
	 * @return a list with speakers of a talk.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findSpeakerByTalk(@QueryParam("talkId") long talkId);

	/**
	 * get all talks by room id.
	 * 
	 * @param roomId
	 *            id of a room which talks to return.
	 * @return a list of talks in a room.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findTalksByRoom(@QueryParam("roomId") long roomId);

	/**
	 * get a room from a talk.
	 * 
	 * @param talkId
	 *            id of a talk which room is to be return.
	 * @return a room of a talk.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findRoomByTalk(@QueryParam("talkId") long talkId);

}
