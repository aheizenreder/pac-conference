/**
 * 
 */
package com.prodyna.pac.conference.talk.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;

import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.location.model.Location;
import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.location.service.RoomService;
import com.prodyna.pac.conference.monitoring.logging.Logged;
import com.prodyna.pac.conference.monitoring.performance.Monitored;
import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.speaker.service.SpeakerService;
import com.prodyna.pac.conference.talk.TalkUtil;
import com.prodyna.pac.conference.talk.model.Talk;
import com.prodyna.pac.conference.talk.model.TalkToRoom;
import com.prodyna.pac.conference.talk.model.TalkToRoomKey;
import com.prodyna.pac.conference.talk.model.TalkToSpeaker;
import com.prodyna.pac.conference.talk.model.TalkToSpeakerKey;
import com.prodyna.pac.conference.talk.service.exception.OccupiedRoomException;
import com.prodyna.pac.conference.talk.service.exception.SpeakerNotAvailableException;
import com.prodyna.pac.conference.talk.service.exception.TalkOutOfConferenceDateBoundsException;
import com.prodyna.pac.conference.talk.service.exception.WrongLocationException;

/**
 * Implementation of TalkService interface.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@Logged
@Monitored
@Stateless
public class TalkServiceImpl implements TalkService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private RoomService roomService;

	@Inject
	private SpeakerService speakerService;

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
	public Talk create(Talk talk) throws TalkOutOfConferenceDateBoundsException {
		log.info("Create new Talk " + talk.getTitle() + " ...");
		// check the date bounds.
		checkTalkDateBounds(talk);

		em.persist(talk);
		em.flush();
		log.info("Talk " + talk.getTitle() + " persisted with id "
				+ talk.getId());

		return talk;
	}

	/**
	 * @param talk
	 * @throws TalkOutOfConferenceDateBoundsException
	 */
	private void checkTalkDateBounds(Talk talk)
			throws TalkOutOfConferenceDateBoundsException {
		Date talkEndDate = TalkUtil.calculateTalkEndDate(talk);
		if (talk.getStartDate().getTime() < talk.getConference().getStartDate()
				.getTime()
				|| talkEndDate.getTime() > talk.getConference().getEndDate()
						.getTime()) {
			// Talk violates bounds of conference.
			throw new TalkOutOfConferenceDateBoundsException(
					"Talk is out of conference date bound!",
					talk.getConference());
		}
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

		TypedQuery<Talk> selectAllTalksQuery = em.createNamedQuery(
				Talk.SELECT_ALL_TALKS, Talk.class);
		List<Talk> resultList = selectAllTalksQuery.getResultList();
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
	public Talk update(Talk talk) throws TalkOutOfConferenceDateBoundsException {
		log.info("Update talk with id " + talk.getId() + " ...");
		checkTalkDateBounds(talk);
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
		// first check for location of talk and room
		Location confLocation = talk.getConference().getLocation();
		Location roomLocation = room.getLocation();

		if (!confLocation.equals(roomLocation)) {
			log.error("Location of the talk and room are not the same!");
			throw new WrongLocationException(
					"Location of the talk and room are not the same!",
					confLocation);
		}
		// next check for room collisions
		TypedQuery<TalkToRoom> findRoomCollisionQuery = em.createNamedQuery(
				TalkToRoom.FIND_ROOMS_COLLISIONS, TalkToRoom.class);
		findRoomCollisionQuery.setParameter(
				TalkToRoom.FIND_ROOMS_COLLISIONS_PARAM_NAME_ROOM_ID,
				room.getId());
		findRoomCollisionQuery.setParameter(
				TalkToRoom.FIND_ROOMS_COLLISIONS_PARAM_NAME_START_DATE,
				talk.getStartDate());
		Date endDate = TalkUtil.calculateTalkEndDate(talk);
		findRoomCollisionQuery.setParameter(
				TalkToRoom.FIND_ROOMS_COLLISIONS_PARAM_NAME_END_DATE, endDate);

		List<TalkToRoom> talkToRoomResultList = findRoomCollisionQuery
				.getResultList();
		// are there any collision found?
		if (talkToRoomResultList == null || talkToRoomResultList.isEmpty()) {

			log.info("Assign talk '" + talk.getTitle() + "' and room '"
					+ room.getName() + "...");
			log.debug("get current versions of talk and room from database ...");
			Room roomCurrentState = roomService.get(room.getId());
			Talk talkCurrentState = get(talk.getId());

			TalkToRoom ttr = new TalkToRoom(talkCurrentState, roomCurrentState);
			// persists assignment for room.
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
		TalkToRoomKey ttrKey = new TalkToRoomKey(talk.getId(), room.getId());
		// look for assignment
		TalkToRoom ttr = em.find(TalkToRoom.class, ttrKey);
		em.remove(ttr);
		em.flush();

		return true;
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
		// first check for speaker collisions
		TypedQuery<TalkToSpeaker> findSpeakerCollisionQuery = em
				.createNamedQuery(TalkToSpeaker.FIND_SPEAKER_COLLISIONS,
						TalkToSpeaker.class);
		findSpeakerCollisionQuery.setParameter(
				TalkToSpeaker.FIND_SPEAKER_COLLISIONS_PARAM_NAME_SPEAKER_ID,
				speaker.getId());
		findSpeakerCollisionQuery.setParameter(
				TalkToSpeaker.FIND_SPEAKER_COLLISIONS_PARAM_NAME_START_DATE,
				talk.getStartDate());
		Date endDate = TalkUtil.calculateTalkEndDate(talk);
		findSpeakerCollisionQuery.setParameter(
				TalkToSpeaker.FIND_SPEAKER_COLLISIONS_PARAM_NAME_END_DATE,
				endDate);

		List<TalkToSpeaker> talkToSpeakerResultList = findSpeakerCollisionQuery
				.getResultList();
		// are there any collision found?
		if (talkToSpeakerResultList == null
				|| talkToSpeakerResultList.isEmpty()) {

			log.info("Assign talk '" + talk.getTitle() + "' and speaker '"
					+ speaker.getName() + "...");
			log.debug("get current versions of talk and speaker from database ...");
			Speaker speakerCurrentState = speakerService.get(speaker.getId());
			Talk talkCurrentState = get(talk.getId());

			TalkToSpeaker ttr = new TalkToSpeaker(talkCurrentState,
					speakerCurrentState);
			// persists assignment for speaker.
			em.persist(ttr);
			em.flush();
		} else {
			// there are collisions
			TalkToSpeaker tts = talkToSpeakerResultList.get(0);
			log.info("Found collision for room '" + tts.getSpeaker().getName()
					+ "' from " + tts.getStartDate() + " to "
					+ tts.getEndDate() + "!");
			throw new SpeakerNotAvailableException("Speaker "
					+ speaker.getName() + "is not available!", tts.getTalk(),
					tts.getStartDate(), tts.getEndDate());
		}
		return true;
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
		TalkToSpeakerKey ttsKey = new TalkToSpeakerKey(talk.getId(),
				speaker.getId());
		// find assignment
		TalkToSpeaker tts = em.find(TalkToSpeaker.class, ttsKey);
		em.remove(tts);
		em.flush();

		return true;
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
		TypedQuery<Talk> findTalksByConference = em.createNamedQuery(
				Talk.FIND_TALKS_FOR_CONFERENCE, Talk.class);
		findTalksByConference.setParameter(
				Talk.FIND_TALKS_FOR_CONFERENCE_PARAM_NAME_CONFERENCE_ID,
				conference.getId());
		List<Talk> talksByConferenceList = findTalksByConference
				.getResultList();

		log.info("Found " + talksByConferenceList.size()
				+ " talks for conference '" + conference.getTitle() + "'.");

		return talksByConferenceList;
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
		TypedQuery<Speaker> findSpeakersByTalk = em.createNamedQuery(
				TalkToSpeaker.FIND_SPEAKERS_BY_TALK, Speaker.class);
		findSpeakersByTalk.setParameter(
				TalkToSpeaker.FIND_SPEAKERS_BY_TALK_PARAM_NAME_TALK_ID,
				talk.getId());
		List<Speaker> speakerByTalkList = findSpeakersByTalk.getResultList();
		log.info("Found " + speakerByTalkList.size() + " speaker for talk '"
				+ talk.getTitle() + "'.");

		return speakerByTalkList;
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
		TypedQuery<Talk> findTalksBySpeaker = em.createNamedQuery(
				TalkToSpeaker.FIND_TALKS_BY_SPEAKER, Talk.class);
		findTalksBySpeaker.setParameter(
				TalkToSpeaker.FIND_TALKS_BY_SPEAKER_PARAM_NAME_SPEAKER_ID,
				speaker.getId());
		List<Talk> talksBySpeakerList = findTalksBySpeaker.getResultList();
		log.info("Found " + talksBySpeakerList.size() + " talks for speaker '"
				+ speaker.getName() + "'.");
		return talksBySpeakerList;
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
		TypedQuery<Talk> findTalksByRoom = em.createNamedQuery(
				TalkToRoom.FIND_TALKS_BY_ROOM, Talk.class);
		findTalksByRoom.setParameter(
				TalkToRoom.FIND_TALKS_BY_ROOM_PARAM_NAME_ROOM_ID, room.getId());
		List<Talk> talksByRoomList = findTalksByRoom.getResultList();

		log.info("Found " + talksByRoomList.size() + " talks in room '"
				+ room.getName() + "'.");
		return talksByRoomList;
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
		TypedQuery<Room> findRoomByTalk = em.createNamedQuery(
				TalkToRoom.FIND_ROOM_BY_TALK, Room.class);
		findRoomByTalk.setParameter(
				TalkToRoom.FIND_ROOM_BY_TALK_PARAM_NAME_TALK_ID, talk.getId());
		List<Room> roomByTalkList = findRoomByTalk.getResultList();

		log.info("Found " + roomByTalkList.size() + " rooms for talk '"
				+ talk.getTitle() + "'.");
		return null;
	}

}
