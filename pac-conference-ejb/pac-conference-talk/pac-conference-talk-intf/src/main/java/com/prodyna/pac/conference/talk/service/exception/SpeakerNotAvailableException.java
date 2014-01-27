/**
 * 
 */
package com.prodyna.pac.conference.talk.service.exception;

import java.util.Date;

import com.prodyna.pac.conference.talk.model.Talk;

/**
 * This exception is thrown if a speaker is not available in a specified time
 * slot.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public class SpeakerNotAvailableException extends Exception {

	/**
	 * generated serialization id.
	 */
	private static final long serialVersionUID = 3302167450595313085L;

	/**
	 * default message for the exception.
	 */
	private static String DEFAULT_EXCEPTION_MESSAGE = "Speaker is not available";

	/**
	 * talk which blocks the speaker.
	 */
	private Talk talk;

	/**
	 * start date and time of a time slot of blocking talk.
	 */
	private Date startDate;

	/**
	 * end date and time of a time slot of blocking talk.
	 */
	private Date endDate;

	/**
	 * Default constructor without parameters.
	 */
	public SpeakerNotAvailableException() {
		super(DEFAULT_EXCEPTION_MESSAGE);
	}

	/**
	 * Constructor with detailed message and collision parameters.
	 * 
	 * @param message
	 *            a detailed message for exception.
	 * @param talk
	 *            the talk, which blocks the speaker.
	 * @param startDate
	 *            start date and time of time slot of blocking talk.
	 * @param endDate
	 *            end date and time of time slot of blocking talk.
	 */
	public SpeakerNotAvailableException(String message, Talk talk,
			Date startDate, Date endDate) {
		super(message);
		this.talk = talk;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Constructor with cause for the exception and collision parameters.
	 * 
	 * @param cause
	 *            the Throwable as the cause for the exception.
	 * @param talk
	 *            the talk, which blocks the speaker.
	 * @param startDate
	 *            start date and time of time slot where of blocking talk.
	 * @param endDate
	 *            end date and time of time slot where of blocking talk.
	 */
	public SpeakerNotAvailableException(Throwable cause, Talk talk,
			Date startDate, Date endDate) {
		super(DEFAULT_EXCEPTION_MESSAGE, cause);
		this.talk = talk;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Constructor with detailed message, cause and collision parameters.
	 * 
	 * @param message
	 *            a detailed message for the exception.
	 * @param cause
	 *            a Throwable as a cause for the exeption.
	 * @param talk
	 *            the talk, which blocks the speaker
	 * @param startDate
	 *            start date and time of time slot of blocking talk.
	 * @param endDate
	 *            end date and time of time slot of blocking talk.
	 */
	public SpeakerNotAvailableException(String message, Throwable cause,
			Talk talk, Date startDate, Date endDate) {
		super(message, cause);
		this.talk = talk;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @return the blocking talk.
	 */
	public Talk getTalk() {
		return talk;
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
