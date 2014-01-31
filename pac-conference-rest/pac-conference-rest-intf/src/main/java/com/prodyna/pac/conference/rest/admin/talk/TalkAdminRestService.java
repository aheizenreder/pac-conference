/**
 * 
 */
package com.prodyna.pac.conference.rest.admin.talk;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.talk.model.Talk;

/**
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface TalkAdminRestService {
	/**
	 * create new talk.
	 * 
	 * @param talk
	 *            talk data to create new talk.
	 * @return the representation of created talk wrapped in a reponse object.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Talk talk);

	/**
	 * updates a talk.
	 * 
	 * @param talk
	 *            talk object with new values for update.
	 * 
	 * @return updated representation of the talk after update wrapped in a
	 *         response object.
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Talk talk);

	/**
	 * deletes a talk identified by its id.
	 * 
	 * @param id
	 *            Id of a talk to delete.
	 * @return deleted talk wrapped in a response object.
	 */
	@DELETE
	@Path("/{id:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id);

	/**
	 * assign a room identified by roomId to a talk identified by talkId.
	 * 
	 * @param talkId
	 *            Id of a talk, which gets a room assigned.
	 * @param roomId
	 *            Id of a room, which is to assign to the talk.
	 * @return true if the room was assigned to the talk, else false. Result
	 *         value is wrapped in a response object.
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignRoom(@QueryParam("talkId") long talkId,
			@QueryParam("roomId") long roomId);

	/**
	 * remove assignment of a room identified by roomId and a talk identified by
	 * talkId.
	 * 
	 * @param talkId
	 *            Id of a talk which assignment is to delete.
	 * @param roomId
	 *            Id of assigned room, which is to remove.
	 * @return true if assignment was deleted successfully, else false. Result
	 *         value is wrapped in a response object.
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response unassignRoom(@QueryParam("talkId") long talkId,
			@QueryParam("roomId") long roomId);

	/**
	 * assign a speaker to a talk. Speaker and talk are identified by their ids.
	 * 
	 * @param talkId
	 *            Id of a talk, which gets a speaker assigned.
	 * @param speakerId
	 *            Id of a speaker, which will be assigned to a talk.
	 * @return true if assignment was successful, else false. Result value is
	 *         wrapped in a response object.
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignSpeaker(@QueryParam("talkId") long talkId,
			@QueryParam("speakerId") long speakerId);

	/**
	 * remove speaker to talk assignment.
	 * 
	 * @param talkId
	 *            Id of talk from assignment to delete.
	 * @param speakerId
	 *            Id of talk from assignment to delete.
	 * @return true if the assignment was deleted successfully, else false.
	 *         Result value is wrapped in a response object.
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response unassignSpeaker(@QueryParam("talkId") long talkId,
			@QueryParam("speakerId") long speakerId);

}
