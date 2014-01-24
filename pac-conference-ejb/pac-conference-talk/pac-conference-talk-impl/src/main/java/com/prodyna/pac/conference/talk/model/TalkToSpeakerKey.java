/**
 * 
 */
package com.prodyna.pac.conference.talk.model;

import java.io.Serializable;

/**
 * This class represents compound key for TalkToSpeaker entity.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public class TalkToSpeakerKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 678084732981027108L;

	/**
	 * talk id as part of the key
	 */
	private Long talk;

	/**
	 * speaker id as part of key
	 */
	private Long speaker;

	/**
	 * 
	 */
	public TalkToSpeakerKey() {
	}

	public TalkToSpeakerKey(Long talk, Long speaker) {
		this.talk = talk;
		this.speaker = speaker;
	}

	/**
	 * @return the talk
	 */
	public Long getTalk() {
		return talk;
	}

	/**
	 * @param talk
	 *            the talk to set
	 */
	public void setTalk(Long talk) {
		this.talk = talk;
	}

	/**
	 * @return the speaker
	 */
	public Long getSpeaker() {
		return speaker;
	}

	/**
	 * @param speaker
	 *            the speaker to set
	 */
	public void setSpeaker(Long speaker) {
		this.speaker = speaker;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((speaker == null) ? 0 : speaker.hashCode());
		result = prime * result + ((talk == null) ? 0 : talk.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TalkToSpeakerKey)) {
			return false;
		}
		TalkToSpeakerKey other = (TalkToSpeakerKey) obj;
		if (speaker == null) {
			if (other.speaker != null) {
				return false;
			}
		} else if (!speaker.equals(other.speaker)) {
			return false;
		}
		if (talk == null) {
			if (other.talk != null) {
				return false;
			}
		} else if (!talk.equals(other.talk)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TalkToSpeakerKey [talk=" + talk + ", speaker=" + speaker + "]";
	}

}
