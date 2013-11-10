/**
 * 
 */
package com.prodyna.pac.conference.location.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.prodyna.pac.conference.location.model.Location;
import com.prodyna.pac.conference.location.model.Room;

/**
 * Implementation of RoomService interface.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 *
 */
@Stateless
public class RoomServiceImpl implements RoomService {

	@Inject
	private Logger log;
	
	@Inject
	private EntityManager em;
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.conference.location.service.RoomService#create(com.prodyna.pac.conference.location.model.Room)
	 */
	@Override
	public Room create(Room room) {
		log.info("Create new room "+room.getName()+" ...");
		em.persist(room);
		log.info("Room "+room.getName()+" persisted with id "+room.getDescription());
		
		return room;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.conference.location.service.RoomService#get(java.lang.Long)
	 */
	@Override
	public Room get(Long id) {
		Room room = null;
		log.info("Get room by id "+id);
		room = em.find(Room.class, id);
		if ( room != null){
			log.info("Found rootm for id "+id+" room name is "+room.getName());
		}
		return room;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.conference.location.service.RoomService#update(com.prodyna.pac.conference.location.model.Room)
	 */
	@Override
	public Room update(Room room) {
		log.info("Update room with id " + room.getId()+" ...");
		em.merge(room);
		log.info("Room with id "+room.getId()+" updated.");
		
		return room;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.conference.location.service.RoomService#delete(com.prodyna.pac.conference.location.model.Room)
	 */
	@Override
	public Room delete(Room room) {
		log.info("Delete room with id "+ room.getId());
		Room rm = em.find(Room.class, room.getId());
		em.remove(rm);
		log.info("Room with id "+rm.getId()+" was deleted.");
		
		return rm;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.conference.location.service.RoomService#findRoomByLocation(com.prodyna.pac.conference.location.model.Location)
	 */
	@Override
	public List<Room> findRoomByLocation(Location location) {
		log.info("Find all rooms for a location with id "+location.getId()+" ...");
		
		Long locationId = location.getId();
		Query findRoomByLocationQuery = em.createNamedQuery(Room.FIND_ROOMS_FOR_LOCATION);
		findRoomByLocationQuery.setParameter(Room.FIND_ROOMS_FOR_LOCATION_PARAM_NAME_LOCATION_ID, locationId);
		List<Room> resultList = (List<Room>) findRoomByLocationQuery.getResultList();
		if ( resultList != null){
			log.info("there are "+resultList.size()+" rooms found.");
		}
		return resultList;
	}

}
