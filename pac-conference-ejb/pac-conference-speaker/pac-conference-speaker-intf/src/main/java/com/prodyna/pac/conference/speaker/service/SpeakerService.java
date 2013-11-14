/**
 * 
 */
package com.prodyna.pac.conference.speaker.service;

import java.util.List;

import com.prodyna.pac.conference.speaker.model.Speaker;

/**
 * Interface definition of service to create, read, update, delete Speaker
 * entities.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface SpeakerService {

	/**
	 * store a speaker in the database.
	 * 
	 * @param speaker
	 *            a instance of Speaker entity to store.
	 * 
	 * @return the Speaker instance after persist.
	 */
	public Speaker create(Speaker speaker);

	/**
	 * reads a speaker by it id.
	 * 
	 * @param id
	 *            the if of the speaker to read.
	 * 
	 * @return the instance of Speaker with the given id.
	 */
	public Speaker get(Long id);

	/**
	 * read all speaker from database.
	 * 
	 * @return a List with Speaker instances from database.
	 */
	public List<Speaker> getAll();

	/**
	 * update a Speaker instance in database with values provided in Speaker
	 * object.
	 * 
	 * @param speaker
	 *            a Speaker instance to update.
	 * 
	 * @return the Speaker instance after update.
	 */
	public Speaker update(Speaker speaker);

	/**
	 * removes a Speaker instance from database.
	 * 
	 * @param speaker
	 *            a Speaker instance to remove.
	 * 
	 * @return removed Speaker instance.
	 */
	public Speaker delete(Speaker speaker);
}
