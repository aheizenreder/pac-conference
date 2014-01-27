package com.prodyna.pac.conference.talk.model;

import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.talk.TalkUtil;
import com.prodyna.pac.conference.talk.model.Talk;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This entity represents the assignment of talks to speakers.
 * 
 */
@IdClass(TalkToSpeakerKey.class)
@Entity
@Table(name = "talk_talk_to_speaker", uniqueConstraints = @UniqueConstraint(columnNames = {
		"talk_id", "speaker_id" }))
@NamedQueries({
		@NamedQuery(name = TalkToSpeaker.FIND_SPEAKERS_BY_TALK, query = "SELECT ts.speaker FROM TalkToSpeaker ts WHERE ts.talk.id = :talkId"),
		@NamedQuery(name = TalkToSpeaker.FIND_TALKS_BY_SPEAKER, query = "SELECT ts.talk FROM TalkToSpeaker ts WHERE ts.speaker.id = :speakerId ORDER BY ts.startDate, ts.endDate"),
		@NamedQuery(name = TalkToSpeaker.FIND_SPEAKER_COLLISIONS, query = "SELECT ts FROM TalkToSpeaker ts WHERE ts.speaker.id = :speakerId AND ((ts.startDate > :startDate and ts.startDate < :endDate) or (ts.endDate > :startDate and ts.endDate < :endDate)) ORDER BY ts.startDate, ts.endDate") })
public class TalkToSpeaker implements Serializable {

	// constants for names in named queries
	public static final String FIND_SPEAKERS_BY_TALK = "findSpeakersByTalk";
	public static final String FIND_SPEAKERS_BY_TALK_PARAM_NAME_TALK_ID = "talkId";
	public static final String FIND_TALKS_BY_SPEAKER = "findTalksBySpeaker";
	public static final String FIND_TALKS_BY_SPEAKER_PARAM_NAME_SPEAKER_ID = "speakerId";
	public static final String FIND_SPEAKER_COLLISIONS = "findSpeakerCollisions";
	public static final String FIND_SPEAKER_COLLISIONS_PARAM_NAME_SPEAKER_ID = "speakerId";
	public static final String FIND_SPEAKER_COLLISIONS_PARAM_NAME_START_DATE = "startDate";
	public static final String FIND_SPEAKER_COLLISIONS_PARAM_NAME_END_DATE = "endDate";
	/**
	 * generated serialization id
	 */
	private static final long serialVersionUID = -3592687454840069887L;

	@Id
	@ManyToOne
	@JoinColumn(name = "talk_id", referencedColumnName = "id")
	private Talk talk;

	@Id
	@ManyToOne
	@JoinColumn(name = "speaker_id", referencedColumnName = "id")
	private Speaker speaker;

	@NotNull
	@Column(name = "start_date")
	private Date startDate;

	@NotNull
	@Column(name = "end_date")
	private Date endDate;

	/**
	 * Default constructor.
	 */
	public TalkToSpeaker() {
		super();
	}

	/**
	 * Constructor with initialization of talk, speaker, start and end date.
	 * Start and end dates are initialized from values of talk. The end date is
	 * calculated from talks start date and talks duration.
	 * 
	 * @param talk
	 *            talk part of the assignment.
	 * @param speaker
	 *            speaker to assign to the talk.
	 */
	public TalkToSpeaker(Talk talk, Speaker speaker) {
		super();
		this.talk = talk;
		this.speaker = speaker;
		this.startDate = talk.getStartDate();
		this.endDate = TalkUtil.calculateTalkEndDate(this.talk);
	}

	/**
	 * get the current talk instance.
	 * 
	 * @return current talk instance.
	 */
	public Talk getTalk() {
		return this.talk;
	}

	/**
	 * set new talk instance. From the start date and duration of new talk the
	 * values for startDate and endDate are initialized. The end date is
	 * calculated based on talks start date and talks duration.
	 * 
	 * @param talk
	 *            new talk instance for the assignment.
	 */
	public void setTalk(Talk talk) {
		this.talk = talk;
		this.startDate = talk.getStartDate();
		this.endDate = TalkUtil.calculateTalkEndDate(this.talk);
	}

	/**
	 * get current instance of speaker.
	 * 
	 * @return the current speaker instance.
	 */
	public Speaker getSpeaker() {
		return this.speaker;
	}

	/**
	 * set new instance for speaker.
	 * 
	 * @param speaker
	 *            new speaker instance for the assignment.
	 */
	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	/**
	 * get the value for start date. The value for start date is set from start
	 * date of talk.
	 * 
	 * @return the startDate for the assignment.
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * get the value for end date. The value for end date is calculated from
	 * talks start date and talks duration.
	 * 
	 * @return the endDate value of end date.
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
		result = prime * result + ((speaker == null) ? 0 : speaker.hashCode());
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
		if (!(obj instanceof TalkToSpeaker)) {
			return false;
		}
		TalkToSpeaker other = (TalkToSpeaker) obj;
		if (endDate == null) {
			if (other.endDate != null) {
				return false;
			}
		} else if (!endDate.equals(other.endDate)) {
			return false;
		}
		if (speaker == null) {
			if (other.speaker != null) {
				return false;
			}
		} else if (!speaker.equals(other.speaker)) {
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
		return "TalkToSpeaker [talk=" + talk + ", speaker=" + speaker
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
