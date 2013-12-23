/**
 * 
 */
package com.prodyna.pac.conference.talk.service;

import java.util.List;

import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.talk.model.Talk;

/**
 * This interface describes the operations of a service for access and
 * management of a conference talk.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface TalkService {

	public Talk create(Talk talk);

	public Talk get(Long id);

	public List<Talk> getAll();

	public Talk update(Talk talk);

	public Talk delete(Talk talk);

	public boolean assignRoom(Talk talk, Room room);

	public boolean unassignRoom(Talk talk, Room room);

	public boolean assignSpeaker(Talk talk, Speaker speaker);

	public boolean unassignSpeaker(Talk talk, Speaker speaker);

	public List<Talk> findTalkByConference(Conference conference);

}
