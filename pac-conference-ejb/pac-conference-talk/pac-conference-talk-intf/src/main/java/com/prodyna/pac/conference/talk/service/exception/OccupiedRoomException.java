/**
 * 
 */
package com.prodyna.pac.conference.talk.service.exception;

import java.util.Date;

import com.prodyna.pac.conference.talk.model.Talk;

/**
 * This exception is thrown if a room is already occupied by other talk in the
 * requested time slot.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public class OccupiedRoomException extends Exception {

	/**
	 * generated serialization id.
	 */
	private static final long serialVersionUID = -2601818825037061677L;

	/**
	 * default message for this exception.
	 */
	private static String DEFAULT_EXCEPTION_MESSAGE = "Room is already occupied";

	/**
	 * talk which occupies the room.
	 */
	private Talk occupyTalk;

	/**
	 * start date and time of occupation.
	 */
	private Date startDate;

	/**
	 * end date and time of occupation.
	 */
	private Date endDate;

	/**
	 * default constructor without parameters. Sets the message to default
	 * value.
	 */
	public OccupiedRoomException() {
		super(DEFAULT_EXCEPTION_MESSAGE);
	}

	/**
	 * Constructor with collision parameters.
	 * 
	 * @param occupyTalk
	 *            the talk, with occupies the room.
	 * @param startDate
	 *            the start date and time for the occupation time slot.
	 * @param endDate
	 *            the end date and time for the occupation time slot.
	 */
	public OccupiedRoomException(Talk occupyTalk, Date startDate, Date endDate) {
		super(DEFAULT_EXCEPTION_MESSAGE);
		this.occupyTalk = occupyTalk;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Constructor with detailed message and collision parameters.
	 * 
	 * @param message
	 *            a detailed message for exception.
	 * @param occupyTalk
	 *            the talk, which occupies the room.
	 * @param startDate
	 *            the start date and time of the occupation time slot.
	 * @param endDate
	 *            the end date and time of the occupation time slot.
	 */
	public OccupiedRoomException(String message, Talk occupyTalk,
			Date startDate, Date endDate) {
		super(message);
		this.occupyTalk = occupyTalk;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Constructor with cause and collision parameters.
	 * 
	 * @param cause
	 *            the cause for the exception.
	 * @param occupyTalk
	 *            the talk, which occupies the room.
	 * @param startDate
	 *            the start date and time of the occupation time slot.
	 * @param endDate
	 *            the end date and time of the occupation time slot.
	 */
	public OccupiedRoomException(Throwable cause, Talk occupyTalk,
			Date startDate, Date endDate) {
		super(DEFAULT_EXCEPTION_MESSAGE, cause);
		this.occupyTalk = occupyTalk;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Constructor with detailed exception message, cause and collision
	 * parameters.
	 * 
	 * @param message
	 *            the detailed message.
	 * @param cause
	 *            the cause for the exception.
	 * @param occupyTalk
	 *            the talk, which occupies the room.
	 * @param startDate
	 *            the start date and time of the occupation time slot.
	 * @param endDate
	 *            the end date and time of the occupation time slog.
	 */
	public OccupiedRoomException(String message, Throwable cause,
			Talk occupyTalk, Date startDate, Date endDate) {
		super(message, cause);
		this.occupyTalk = occupyTalk;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @return the occupyTalk
	 */
	public Talk getOccupyTalk() {
		return occupyTalk;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
}
