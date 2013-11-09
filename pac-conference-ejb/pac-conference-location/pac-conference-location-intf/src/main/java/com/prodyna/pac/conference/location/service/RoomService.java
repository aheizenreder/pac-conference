/**
 * 
 */
package com.prodyna.pac.conference.location.service;

import java.util.List;

import com.prodyna.pac.conference.location.model.Location;
import com.prodyna.pac.conference.location.model.Room;

/**
 * RoomService provides functionality to create, delete, update and select a
 * instance of Room Entity. It provides finder operations too.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface RoomService {

	/**
	 * persists a Room instance in database.
	 * 
	 * @param room
	 *            a Room instance to store in database.
	 * 
	 * @return a Room instance after persist in the database. Some of the fields
	 *         in the returned instance can be updated with from database
	 *         automatic generated values.
	 */
	public Room create(Room room);

	/**
	 * reads a Room instance from database by its id.
	 * 
	 * @param id
	 *            a Long value represents the id of a Room instance.
	 * 
	 * @return a Room instance with given id or null, if no Room instance
	 *         exsists for provided id.
	 */
	public Room get(Long id);

	/**
	 * update a Room instance.
	 * 
	 * @param room
	 *            a Room instance with updated values, which are to update in
	 *            database too.
	 * 
	 * @return a Room instance after update.
	 */
	public Room update(Room room);

	/**
	 * deletes the provided Room instance from database.
	 * 
	 * @param room
	 *            a Room instance to delete.
	 * 
	 * @return the same Room instance which was deleted from database.
	 */
	public Room delete(Room room);

	/**
	 * finds all Rooms for the provided location.
	 * 
	 * @param location
	 *            a Location instance for which the Rooms instances have to be
	 *            found.
	 * 
	 * @return a list with all Rooms for provided Location instance. If no Room
	 *         instances for Location can be find, then is the list empty.
	 * 
	 */
	public List<Room> findRoomByLocation(Location location);
}
