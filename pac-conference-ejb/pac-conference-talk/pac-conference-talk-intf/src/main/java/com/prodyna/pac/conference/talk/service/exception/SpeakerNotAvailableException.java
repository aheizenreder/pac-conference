/**
 * 
 */
package com.prodyna.pac.conference.talk.service.exception;

import java.util.Date;

import com.prodyna.pac.conference.speaker.model.Speaker;

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
	 * speaker which was not available for assignment to a talk.
	 */
	private Speaker speaker;

	/**
	 * start date and time of a time slot where the speaker is not available.
	 */
	private Date startDate;

	/**
	 * end date and time of a time slot where the speaker is not available.
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
	 * @param speaker
	 *            the speaker, which is not available.
	 * @param startDate
	 *            start date and time of time slot where speaker is not
	 *            available.
	 * @param endDate
	 *            end date and time of time slot where speaker is not available.
	 */
	public SpeakerNotAvailableException(String message, Speaker speaker,
			Date startDate, Date endDate) {
		super(message);
		this.speaker = speaker;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Constructor with cause for the exception and collision parameters.
	 * 
	 * @param cause
	 *            the Throwable as the cause for the exception.
	 * @param speaker
	 *            the speaker, which is not available.
	 * @param startDate
	 *            start date and time of time slot where speaker is not
	 *            available.
	 * @param endDate
	 *            end date and time of time slot where speaker is not available.
	 */
	public SpeakerNotAvailableException(Throwable cause, Speaker speaker,
			Date startDate, Date endDate) {
		super(DEFAULT_EXCEPTION_MESSAGE, cause);
		this.speaker = speaker;
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
	 * @param speaker
	 *            the speaker which is not available.
	 * @param startDate
	 *            start date and time of time slot where speaker is not
	 *            available.
	 * @param endDate
	 *            end date and time of time slot where speaker is not available.
	 */
	public SpeakerNotAvailableException(String message, Throwable cause,
			Speaker speaker, Date startDate, Date endDate) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the speaker
	 */
	public Speaker getSpeaker() {
		return speaker;
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
