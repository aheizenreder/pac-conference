/**
 * 
 */
package com.prodyna.pac.conference.talk.service.exception;

import com.prodyna.pac.conference.conference.model.Conference;

/**
 * This Exception is thrown, if a talk is out of date bounds of a conference.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
/**
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public class TalkOutOfConferenceDateBoundsException extends Exception {

	/**
	 * generated value.
	 */
	private static final long serialVersionUID = 8971537085906195109L;

	/**
	 * Default exception message.
	 */
	public static final String DEFAULT_EXCEPTION_MESSAGE = "The talk is out of conference bounds!";

	/**
	 * The conference, which bounds the talk violates
	 */
	private Conference conference;

	/**
	 * Default constructor. It sets as message the value of
	 * DEFAULT_EXCEPTION_MESSAGE.
	 */
	public TalkOutOfConferenceDateBoundsException() {
		super(DEFAULT_EXCEPTION_MESSAGE);
	}

	/**
	 * Constructor with conference, which date bounds the talk violates. As
	 * detailed message the value of DEFAULT_EXCEPTION_MESSAGE is set.
	 * 
	 * @param conference
	 *            the conference which date bound the talk violates.
	 */
	public TalkOutOfConferenceDateBoundsException(Conference conference) {
		super(DEFAULT_EXCEPTION_MESSAGE);
		this.conference = conference;
	}

	/**
	 * Constructor with a detailed message and conference.
	 * 
	 * @param message
	 *            the detailed message.
	 * @param conference
	 *            the conference which bounds talks violates.
	 */
	public TalkOutOfConferenceDateBoundsException(String message, Conference conference) {
		super(message);
		this.conference = conference;
	}

	/**
	 * Constructor with cause and conference.
	 * 
	 * @param cause
	 *            the cause for the exception.
	 * @param conference
	 *            the conference which date bounds the talk violates.
	 */
	public TalkOutOfConferenceDateBoundsException(Throwable cause, Conference conference) {
		super(DEFAULT_EXCEPTION_MESSAGE, cause);
		this.conference = conference;

	}

	/**
	 * Constructor with message, cause and conference.
	 * 
	 * @param message
	 *            the detailed message
	 * @param cause
	 *            the cause for the exception.
	 * @param conference
	 *            the conference which date bounds the talk violates.
	 */
	public TalkOutOfConferenceDateBoundsException(String message, Throwable cause,
			Conference conference) {
		super(message, cause);
		this.conference = conference;
	}

	/**
	 * @return the conference
	 */
	public Conference getConference() {
		return conference;
	}

}
