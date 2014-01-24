/**
 * 
 */
package com.prodyna.pac.conference.talk.model;

import java.io.Serializable;

/**
 * This class represents compound key for TalkToRoom entity.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public class TalkToRoomKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1529491601882305294L;

	private Long talk;

	private Long room;

	/**
	 * 
	 */
	public TalkToRoomKey() {
	}

	public TalkToRoomKey(Long talk, Long room) {
		this.talk = talk;
		this.room = room;
	}

	/**
	 * @return the talk id
	 */
	public Long getTalk() {
		return talk;
	}

	/**
	 * @param talk
	 *            the talk id to set
	 */
	public void setTalkId(Long talk) {
		this.talk = talk;
	}

	/**
	 * @return the room id
	 */
	public Long getRoom() {
		return room;
	}

	/**
	 * @param room
	 *            the room id to set
	 */
	public void setRoom(Long room) {
		this.room = room;
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
		result = prime * result + ((room == null) ? 0 : room.hashCode());
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
		if (!(obj instanceof TalkToRoomKey)) {
			return false;
		}
		TalkToRoomKey other = (TalkToRoomKey) obj;
		if (room == null) {
			if (other.room != null) {
				return false;
			}
		} else if (!room.equals(other.room)) {
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
		return "TalkToRoomKey [talkId=" + talk + ", roomId=" + room + "]";
	}

}
