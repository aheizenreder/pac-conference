package com.prodyna.pac.conference.talk.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.talk.TalkUtil;

/**
 * This entity represents the assignment of talks and rooms. It contains
 * additional information to the start and end date of the assignment.
 * 
 */
@Entity
@Table(name = "talk_talk_to_room", uniqueConstraints = @UniqueConstraint(columnNames = "talk_id, room_id"))
@NamedQueries({
		@NamedQuery(name = TalkToRoom.FIND_TALKS_BY_ROOM, query = "SELECT tr.talk FROM TalkToRoom tr WHERE tr.room.id = :roomId ORDER BY tr.startDate, tr.endDate"),
		@NamedQuery(name = TalkToRoom.FIND_ROOM_BY_TALK, query = "SELECT tr.room FROM TalkToRoom tr WHERE tr.talk.id = :talkId"),
		@NamedQuery(name = TalkToRoom.FIND_ROOMS_COLLISIONS, query = "SELECT tr FROM TalkToRoom tr where tr.room.id = :roomId AND ((tr.startDate > :startDate AND tr.startDate < :endDate) OR (tr.endDate > :startDate and tr.endDate < :endDate)) ORDER BY tr.startDate, tr.endDate") })
public class TalkToRoom implements Serializable {

	public static final String FIND_TALKS_BY_ROOM = "findTalksByRoom";
	public static final String FIND_TALKS_BY_ROOM_PARAM_NAME_ROOM_ID = "roomId";
	public static final String FIND_ROOM_BY_TALK = "findRoomByTalk";
	public static final String FIND_ROOM_BY_TALK_PARAM_NAME_TALK_ID = "talkId";
	public static final String FIND_ROOMS_COLLISIONS = "findRoomsCollisions";
	public static final String FIND_ROOMS_COLLISIONS_PARAM_NAME_ROOM_ID = "roomId";
	public static final String FIND_ROOMS_COLLISIONS_PARAM_NAME_START_DATE = "startDate";
	public static final String FIND_ROOMS_COLLISIONS_PARAM_NAME_END_DATE = "endDate";

	/**
	 * generated serialization id.
	 */
	private static final long serialVersionUID = -9073687415635231704L;

	@Id
	@ManyToOne
	@JoinColumn(name = "talk_id", referencedColumnName = "id")
	private Talk talk;

	@Id
	@ManyToOne
	@JoinColumn(name = "room_id", referencedColumnName = "id")
	private Room room;

	@NotNull
	@Column(name = "start_date")
	private Date startDate;

	@NotNull
	@Column(name = "end_date")
	private Date endDate;

	/**
	 * Default constructor without parameters.
	 */
	public TalkToRoom() {
		super();
	}

	/**
	 * Constructor with initialization of talk and room by given parameters.
	 * startDate and endDate are also initialized based on data from talk.
	 * 
	 * @param talk
	 *            a talk.
	 * @param room
	 *            a room.
	 */
	public TalkToRoom(Talk talk, Room room) {
		super();
		this.talk = talk;
		this.room = room;
		this.startDate = talk.getStartDate();
		this.endDate = TalkUtil.calculateTalkEndDate(this.talk);
	}

	/**
	 * get current instance of talk.
	 * 
	 * @return the talk.
	 */
	public Talk getTalk() {
		return talk;
	}

	/**
	 * set new instance of talk. In parallel the start and end date are set to
	 * the start date of talk and based on talk data calculated end date.
	 * 
	 * @param talk
	 *            the talk to set
	 */
	public void setTalk(Talk talk) {
		this.talk = talk;
		this.startDate = talk.getStartDate();
		this.endDate = TalkUtil.calculateTalkEndDate(this.talk);
	}

	/**
	 * get current instance of talk.
	 * 
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * set new room to the assignment.
	 * 
	 * @param room
	 *            the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * get start date of assignment.
	 * 
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * get end date of assignment.
	 * 
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
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
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
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
		if (!(obj instanceof TalkToRoom)) {
			return false;
		}
		TalkToRoom other = (TalkToRoom) obj;
		if (endDate == null) {
			if (other.endDate != null) {
				return false;
			}
		} else if (!endDate.equals(other.endDate)) {
			return false;
		}
		if (room == null) {
			if (other.room != null) {
				return false;
			}
		} else if (!room.equals(other.room)) {
			return false;
		}
		if (startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!startDate.equals(other.startDate)) {
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
		return "TalkToRoom [talk=" + talk + ", room=" + room + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
	}

}
