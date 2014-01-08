/**
 * 
 */
package com.prodyna.pac.conference.talk.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.talk.TalkUtil;
import com.prodyna.pac.conference.talk.model.Talk;
import com.prodyna.pac.conference.talk.model.TalkToRoom;
import com.prodyna.pac.conference.talk.service.exception.OccupiedRoomException;
import com.prodyna.pac.conference.talk.service.exception.SpeakerNotAvailableException;
import com.prodyna.pac.conference.talk.service.exception.WrongLocationException;

/**
 * Implementation of TalkService interface.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@Stateless
public class TalkServiceImpl implements TalkService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	/**
	 * Default empty constructor.
	 */
	public TalkServiceImpl() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#create(com.prodyna
	 * .pac.conference.talk.model.Talk)
	 */
	@Override
	public Talk create(Talk talk) {
		log.info("Create new Talk " + talk.getTitle() + " ...");
		em.persist(talk);
		log.info("Talk " + talk.getTitle() + " persisted with id "
				+ talk.getId());

		return talk;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#get(java.lang.Long)
	 */
	@Override
	public Talk get(Long id) {
		Talk talk = null;
		log.info("Get talk by id " + id);
		talk = em.find(Talk.class, id);
		if (talk != null) {
			log.info("Found talk for id " + id + " talk name is "
					+ talk.getTitle());
		}
		return talk;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.talk.service.TalkService#getAll()
	 */
	@Override
	public List<Talk> getAll() {
		log.info("Select all talks ...");

		Query selectAllTalksQuery = em.createNamedQuery(Talk.SELECT_ALL_TALKS);
		List<Talk> resultList = (List<Talk>) selectAllTalksQuery
				.getResultList();
		if (resultList != null) {
			log.info("there are " + resultList.size() + " talks found.");
		}
		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#update(com.prodyna
	 * .pac.conference.talk.model.Talk)
	 */
	@Override
	public Talk update(Talk talk) {
		log.info("Update talk with id " + talk.getId() + " ...");
		em.merge(talk);
		log.info("Talk with id " + talk.getId() + " updated.");

		return talk;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#delete(com.prodyna
	 * .pac.conference.talk.model.Talk)
	 */
	@Override
	public Talk delete(Talk talk) {
		log.info("Delete talk with id " + talk.getId());
		Talk tk = em.find(Talk.class, talk.getId());
		em.remove(tk);
		log.info("Talk with id " + tk.getId() + " was deleted.");

		return tk;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#assignRoom(com.prodyna
	 * .pac.conference.talk.model.Talk,
	 * com.prodyna.pac.conference.location.model.Room)
	 */
	@Override
	public boolean assignRoom(Talk talk, Room room)
			throws WrongLocationException, OccupiedRoomException {
		// first check for room collisions
		Query findRoomCollisionQuery = em
				.createNamedQuery(TalkToRoom.FIND_ROOMS_COLLISIONS);
		findRoomCollisionQuery.setParameter(
				TalkToRoom.FIND_ROOMS_COLLISIONS_PARAM_NAME_ROOM_ID,
				room.getId());
		findRoomCollisionQuery.setParameter(
				TalkToRoom.FIND_ROOMS_COLLISIONS_PARAM_NAME_START_DATE,
				talk.getStartDate());
		Date endDate = TalkUtil.calculateTalkEndDate(talk);
		findRoomCollisionQuery.setParameter(
				TalkToRoom.FIND_ROOMS_COLLISIONS_PARAM_NAME_END_DATE, endDate);

		List<TalkToRoom> talkToRoomResultList = (List<TalkToRoom>) findRoomCollisionQuery
				.getResultList();
		// are there any collision found?
		if (talkToRoomResultList == null || talkToRoomResultList.isEmpty()) {
			// persists assignment for room.
			log.info("Assign talk '" + talk.getTitle() + "' and room '"
					+ room.getName() + "...");
			TalkToRoom ttr = new TalkToRoom(talk, room);
			em.persist(ttr);
			em.flush();
		} else {
			// there are collisions
			TalkToRoom ttr = talkToRoomResultList.get(0);
			log.info("Found collision for room '" + ttr.getRoom().getName()
					+ "' from " + ttr.getStartDate() + " to "
					+ ttr.getEndDate() + "!");
			throw new OccupiedRoomException(ttr.getTalk(), ttr.getStartDate(),
					ttr.getEndDate());
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#unassignRoom(com.
	 * prodyna.pac.conference.talk.model.Talk,
	 * com.prodyna.pac.conference.location.model.Room)
	 */
	@Override
	public boolean unassignRoom(Talk talk, Room room) {

		
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#assignSpeaker(com
	 * .prodyna.pac.conference.talk.model.Talk,
	 * com.prodyna.pac.conference.speaker.model.Speaker)
	 */
	@Override
	public boolean assignSpeaker(Talk talk, Speaker speaker)
			throws SpeakerNotAvailableException {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#unassignSpeaker(com
	 * .prodyna.pac.conference.talk.model.Talk,
	 * com.prodyna.pac.conference.speaker.model.Speaker)
	 */
	@Override
	public boolean unassignSpeaker(Talk talk, Speaker speaker) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#findTalksByConference
	 * (com.prodyna.pac.conference.conference.model.Conference)
	 */
	@Override
	public List<Talk> findTalksByConference(Conference conference) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#findSpeakerByTalk
	 * (com.prodyna.pac.conference.talk.model.Talk)
	 */
	@Override
	public List<Speaker> findSpeakerByTalk(Talk talk) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#findTalksBySpeaker
	 * (com.prodyna.pac.conference.speaker.model.Speaker)
	 */
	@Override
	public List<Talk> findTalksBySpeaker(Speaker speaker) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#findTalksByRoom(com
	 * .prodyna.pac.conference.location.model.Room)
	 */
	@Override
	public List<Talk> findTalksByRoom(Room room) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#findRoomByTalk(com
	 * .prodyna.pac.conference.talk.model.Talk)
	 */
	@Override
	public Room findRoomByTalk(Talk talk) {
		// TODO Auto-generated method stub
		return null;
	}

}
