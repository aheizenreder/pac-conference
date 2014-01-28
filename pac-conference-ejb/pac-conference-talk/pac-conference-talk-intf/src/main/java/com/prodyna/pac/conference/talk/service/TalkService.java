/**
 * 
 */
package com.prodyna.pac.conference.talk.service;

import java.util.List;

import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.talk.model.Talk;
import com.prodyna.pac.conference.talk.service.exception.OccupiedRoomException;
import com.prodyna.pac.conference.talk.service.exception.SpeakerNotAvailableException;
import com.prodyna.pac.conference.talk.service.exception.TalkOutOfConferenceDateBoundsException;
import com.prodyna.pac.conference.talk.service.exception.WrongLocationException;

/**
 * This interface describes the operations of a service for access and
 * management of a conference talk.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface TalkService {

	/**
	 * persists a conference talk in database
	 * 
	 * @param talk
	 *            a conference talk to persists.
	 * @return talk instance after persist.
	 */
	/**
	 * @param talk
	 *            a conference talk to persists.
	 * @return talk instance after persist.
	 * @throws TalkOutOfConferenceDateBoundsException
	 *             if the talk violates the date bound of the conference.
	 */
	public Talk create(Talk talk) throws TalkOutOfConferenceDateBoundsException;

	/**
	 * reads a conference talk from database by it's ID.
	 * 
	 * @param id
	 *            a ID of a conference talk to read.
	 * 
	 * @return a talk instance read from database by its id. Return is null if
	 *         no talk was found for given id.
	 */
	public Talk get(Long id);

	/**
	 * reads all existing conference talks from database.
	 * 
	 * @return a list with all in database existing conference talk instances.
	 *         If no talks exists yet is the result list empty.
	 */
	public List<Talk> getAll();

	/**
	 * update given conference talk in database.
	 * 
	 * @param talk
	 *            a conference talk with new data to persist.
	 * 
	 * @return the talk instance after update in database.
	 * @throws TalkOutOfConferenceDateBoundsException
	 *             if the new value for talks date bounds violate the conference
	 *             date bounds.
	 */
	public Talk update(Talk talk) throws TalkOutOfConferenceDateBoundsException;

	/**
	 * deletes given talk instance from database.
	 * 
	 * @param talk
	 *            a conference talk to delete.
	 * 
	 * @return the deleted talk instance.
	 */
	public Talk delete(Talk talk);

	/**
	 * assign a room to a talk. It checks if the room is in the same location as
	 * the conference of a talk and throws WrongLocationExcetpion. If the room
	 * is already occupied by an other talk, then the OccupiedRoomException will
	 * be thrown.
	 * 
	 * @param talk
	 *            talk which get the room assigned.
	 * @param room
	 *            the room which gets a talk assigned.
	 * @return true if the the room can be assigned to the talk. Else false.
	 * @throws WrongLocationException
	 *             if the locations of talk (used from conference) and room are
	 *             not the same.
	 * @throws OccupiedRoomException
	 *             if the location is already occupied by an other talk.
	 */
	public boolean assignRoom(Talk talk, Room room)
			throws WrongLocationException, OccupiedRoomException;

	/**
	 * remove the assignment of a room to a talk.
	 * 
	 * @param talk
	 *            the talk to remove the room assignment.
	 * @param room
	 *            the room to remove from talk assignment.
	 * 
	 * @return true if the assignment can be removed.
	 */
	public boolean unassignRoom(Talk talk, Room room);

	/**
	 * assign a speaker to a talk. It checks if the speaker is available for the
	 * time of the talk. If not a SpeakerNotAvailableException will be thrown.
	 * 
	 * @param talk
	 *            a talk which gets a speaker assigned.
	 * @param speaker
	 *            a speaker to assign to a talk.
	 * @return true if the assignment was successful.
	 * @throws SpeakerNotAvailableException
	 *             if the speaker is not available for the talks time slot.
	 */
	public boolean assignSpeaker(Talk talk, Speaker speaker)
			throws SpeakerNotAvailableException;

	/**
	 * remove the assignment of a speaker to a talk.
	 * 
	 * @param talk
	 *            the talk to remove the speaker assignment.
	 * @param speaker
	 *            the speaker to remove from talk assignment.
	 * @return true if the assignment was removed successful.
	 */
	public boolean unassignSpeaker(Talk talk, Speaker speaker);

	/**
	 * get all talks for a conference.
	 * 
	 * @param conference
	 *            a conference for which all talks are to return.
	 * @return a list with all talks for the given conference. If no talks for
	 *         the given conference exist, then the empty list will be returned.
	 */
	public List<Talk> findTalksByConference(Conference conference);

	/**
	 * get all Speaker for a talk.
	 * 
	 * @param talk
	 *            a talk which speaker are to return.
	 * @return a list with all speakers of the given talk. If no speaker are
	 *         assigned then a empty list will be returned.
	 */
	public List<Speaker> findSpeakerByTalk(Talk talk);

	/**
	 * get all Talks for a Speaker.
	 * 
	 * @param speaker
	 *            a speaker which talks have to be returned.
	 * @return a list with all talks hold by given speaker. If no talks are
	 *         found for the speaker, then a empty list will be returned.
	 */
	public List<Talk> findTalksBySpeaker(Speaker speaker);

	/**
	 * get all Talks assigned to a room.
	 * 
	 * @param room
	 *            a room for which all talks are to return.
	 * @return a list with all talks assigned to the given room. If no talk take
	 *         place in the room, then a empty list will be returned.
	 */
	public List<Talk> findTalksByRoom(Room room);

	/**
	 * get the room assigned to the given talk.
	 * 
	 * @param talk
	 *            talk which room is to return.
	 * @return the room assigned to the talk. If no room is assigned to the
	 *         talk, then return is null.
	 */
	public Room findRoomByTalk(Talk talk);

}
