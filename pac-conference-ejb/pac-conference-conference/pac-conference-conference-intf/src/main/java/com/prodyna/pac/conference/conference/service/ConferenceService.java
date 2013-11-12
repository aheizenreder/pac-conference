/**
 * 
 */
package com.prodyna.pac.conference.conference.service;

import java.util.Date;
import java.util.List;

import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.location.model.Location;

/**
 * Interface for service to create, read, update and delete conference data.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface ConferenceService {

	/**
	 * persists a conference element.
	 * 
	 * @param conference
	 *            instance of a Conference to persists.
	 * 
	 * @return the instance of Conference after persist.
	 */
	public Conference create(Conference conference);

	/**
	 * read a conference by its id.
	 * 
	 * @param id
	 *            Id of a conference to read from database.
	 * 
	 * @return read instance of a conference with given id.
	 */
	public Conference get(Long id);

	/**
	 * read all conferences instances.
	 * 
	 * @return a list of conferences instances.
	 */
	public List<Conference> getAll();

	/**
	 * update a conference with new values.
	 * 
	 * @param conference
	 *            a Conference instance to update.
	 * @return updated conference instance.
	 */
	public Conference update(Conference conference);

	/**
	 * deletes a Conference instance from database.
	 * 
	 * @param conference
	 *            a instance to delete.
	 * @return deleted Conference instance.
	 */
	public Conference delete(Conference conference);

	/**
	 * returns a list of Conferences for a specified location.
	 * 
	 * @param location
	 *            a location for select conferences placed in this location.
	 * @return a list of conferences selected by given location. If no
	 *         conferences are found a empty list will be returned.
	 */
	public List<Conference> findConferenceByLocation(Location location);

	/**
	 * returns a list of conferences which started on or after as parameter
	 * specified start date.
	 * 
	 * @param startDate
	 *            a date to find conferences started after.
	 * 
	 * @return a list with all conferences started on or after given start date.
	 */
	public List<Conference> findConferencesStartedAfterDate(
			Date startDate);

	/**
	 * returns a list of conferences for a specified locaiton and started on or
	 * after given startDate.
	 * 
	 * @param location
	 *            a Location instance, for which the conference have to be
	 *            found.
	 * @param startDate
	 *            a Date on which or after which the conference started.
	 * 
	 * @return a List with conferences which placed in provided Location and
	 *         started on or after provided start date.
	 */
	public List<Conference> findConferencesByStartDateLocation(
			Location location, Date startDate);
}
