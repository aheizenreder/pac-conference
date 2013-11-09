/**
 * 
 */
package com.prodyna.pac.conference.location.service;

import com.prodyna.pac.conference.location.model.Location;

/**
 * LocationService provides CRUD functionality for Entity Location.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */

public interface LocationService {

	/**
	 * persists the instance of location in database.
	 * 
	 * @param location
	 *            a Location instance to persist.
	 * 
	 * @return the instance of Location stored in database. This instance is
	 *         updated with the valued automatic generated by database (e.g.
	 *         id).
	 */
	public Location create(Location location);

	/**
	 * read a Location by its id.
	 * 
	 * @param id
	 *            a Long value represens the id of a location. It may not be
	 *            null or negative.
	 * 
	 * @return a location instance with corresponding id or null, if no location
	 *         with given id exists.
	 */
	public Location get(Long id);

	
	/**
	 * update a location instance.
	 * 
	 * @param location a Location instance with updated values.
	 * 
	 * @return the updated version of Location.
	 */
	public Location update(Location location);

	
	/**
	 * deletes the given Location from database.
	 * 
	 * @param location Location instance to delete.
	 * 
	 * @return the same Location instance which was removed from database.
	 */
	public Location delete(Location location);

}
