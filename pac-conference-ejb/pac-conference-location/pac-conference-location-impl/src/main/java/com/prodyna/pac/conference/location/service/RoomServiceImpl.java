/**
 * 
 */
package com.prodyna.pac.conference.location.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;

import com.prodyna.pac.conference.location.model.Location;
import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.monitoring.logging.Logged;
import com.prodyna.pac.conference.monitoring.performance.Monitored;

/**
 * Implementation of RoomService interface.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@Logged
@Monitored
@Stateless
public class RoomServiceImpl implements RoomService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.location.service.RoomService#create(com.prodyna
	 * .pac.conference.location.model.Room)
	 */
	@Override
	public Room create(Room room) {
		log.info("Create new room " + room.getName() + " ...");
		em.persist(room);
		log.info("Room " + room.getName() + " persisted with id "
				+ room.getId());

		return room;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.location.service.RoomService#get(java.lang
	 * .Long)
	 */
	@Override
	public Room get(Long id) {
		Room room = null;
		log.info("Get room by id " + id);
		room = em.find(Room.class, id);
		if (room != null) {
			log.info("Found root for id " + id + " room name is "
					+ room.getName());
		}
		return room;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.location.service.RoomService#update(com.prodyna
	 * .pac.conference.location.model.Room)
	 */
	@Override
	public Room update(Room room) {
		log.info("Update room with id " + room.getId() + " ...");
		em.merge(room);
		log.info("Room with id " + room.getId() + " updated.");

		return room;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.location.service.RoomService#delete(com.prodyna
	 * .pac.conference.location.model.Room)
	 */
	@Override
	public Room delete(Room room) {
		log.info("Delete room with id " + room.getId());
		Room rm = em.find(Room.class, room.getId());
		em.remove(rm);
		log.info("Room with id " + rm.getId() + " was deleted.");

		return rm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.location.service.RoomService#findRoomByLocation
	 * (com.prodyna.pac.conference.location.model.Location)
	 */
	@Override
	public List<Room> findRoomByLocation(Location location) {
		log.info("Find all rooms for a location with id " + location.getId()
				+ " ...");

		Long locationId = location.getId();
		TypedQuery<Room> findRoomByLocationQuery = em.createNamedQuery(
				Room.FIND_ROOMS_FOR_LOCATION, Room.class);
		findRoomByLocationQuery
				.setParameter(
						Room.FIND_ROOMS_FOR_LOCATION_PARAM_NAME_LOCATION_ID,
						locationId);
		List<Room> resultList = findRoomByLocationQuery.getResultList();
		if (resultList != null) {
			log.info("there are " + resultList.size() + " rooms found.");
		}
		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.location.service.RoomService#getAll()
	 */
	@Override
	public List<Room> getAll() {
		log.info("Select all rooms ...");

		TypedQuery<Room> selectAllRoomsQuery = em.createNamedQuery(
				Room.SELECT_ALL_ROOMS, Room.class);
		List<Room> resultList = selectAllRoomsQuery.getResultList();
		if (resultList != null) {
			log.info("there are " + resultList.size() + " rooms found.");
		}
		return resultList;
	}

}
