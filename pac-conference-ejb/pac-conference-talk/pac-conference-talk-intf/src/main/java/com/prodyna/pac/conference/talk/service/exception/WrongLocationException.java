/**
 * 
 */
package com.prodyna.pac.conference.talk.service.exception;

import com.prodyna.pac.conference.location.model.Location;

/**
 * This exception is thrown if the locations of a talk and of a room, which are
 * to assign to each other, are different.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public class WrongLocationException extends Exception {

	/**
	 * generated value for serialisation.
	 */
	private static final long serialVersionUID = -1139548056890267835L;

	/**
	 * Default message for this exception type.
	 */
	private static final String DEFAULT_EXCEPTION_MESSAGE = "Wrong location";

	/**
	 * instance of expected location. Is the same instance as from conference.
	 */
	private Location expectedLocation;

	/**
	 * default constructor without parameters
	 */
	public WrongLocationException() {
		super(DEFAULT_EXCEPTION_MESSAGE);
	}

	/**
	 * Constructor with the instance of the expected location.
	 * 
	 * @param expectedLocation
	 *            instance of expected location.
	 */
	public WrongLocationException(Location expectedLocation) {
		super(DEFAULT_EXCEPTION_MESSAGE);
		this.expectedLocation = expectedLocation;
	}

	/**
	 * Constructor with specified exception message.
	 * 
	 * @param message
	 *            the message for new exception.
	 * @param expectedLocation
	 *            instance of expected location.
	 */
	public WrongLocationException(String message, Location expectedLocation) {
		super(message);
		this.expectedLocation = expectedLocation;
	}

	/**
	 * Constructor with cause as parameter
	 * 
	 * @param cause
	 *            a Throwable as cause for new exception.
	 * @param expectedLocation
	 *            instance of expected location.
	 */
	public WrongLocationException(Throwable cause, Location expectedLocation) {
		super(DEFAULT_EXCEPTION_MESSAGE, cause);
		this.expectedLocation = expectedLocation;
	}

	/**
	 * Constructor with message and cause.
	 * 
	 * @param message
	 *            the message for new exception.
	 * @param cause
	 *            a Throwable as a cause for new exception.
	 * @param expectedLocation
	 *            instance of expected location.
	 */
	public WrongLocationException(String message, Throwable cause,
			Location expectedLocation) {
		super(message, cause);
		this.expectedLocation = expectedLocation;
	}

	/**
	 * returns the expected location.
	 * 
	 * @return the expectedLocation
	 */
	public Location getExpectedLocation() {
		return expectedLocation;
	}

}
