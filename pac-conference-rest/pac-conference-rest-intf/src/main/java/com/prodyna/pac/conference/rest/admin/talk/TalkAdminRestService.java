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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.talk.model.Talk;

/**
 * Interface for admin rest service for TalkService.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface TalkAdminRestService{
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
	 * @param talk
	 *            a talk, which gets a room assigned.
	 * @param room
	 *            a room, which is to assign to the talk.
	 * @return true if the room was assigned to the talk, else false. Result
	 *         value is wrapped in a response object.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignRoom(Talk talk, Room room);

	/**
	 * remove assignment of a room identified by roomId and a talk identified by
	 * talkId.
	 * 
	 * @param talk
	 *            a talk which assignment is to delete.
	 * @param room
	 *            assigned room, which is to remove.
	 * @return true if assignment was deleted successfully, else false. Result
	 *         value is wrapped in a response object.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response unassignRoom(Talk talk, Room room);

	/**
	 * assign a speaker to a talk. Speaker and talk are identified by their ids.
	 * 
	 * @param talk
	 *            a talk, which gets a speaker assigned.
	 * @param speaker
	 *            a speaker, which will be assigned to a talk.
	 * @return true if assignment was successful, else false. Result value is
	 *         wrapped in a response object.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignSpeaker(Talk talk, Speaker speaker);

	/**
	 * remove speaker to talk assignment.
	 * 
	 * @param talk
	 *            a talk from assignment to delete.
	 * @param speaker
	 *            a speaker from assignment to delete.
	 * @return true if the assignment was deleted successfully, else false.
	 *         Result value is wrapped in a response object.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response unassignSpeaker(Talk talk, Speaker speaker);

}
