/**
 * 
 */
package com.prodyna.pac.conference.talk.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity Talk represents a conference talk.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@Entity
@Table(name = "talk_talk", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@NamedQueries(@NamedQuery(name = Talk.SELECT_ALL_TALKS, query = "SELECT t FROM Talk t"))
public class Talk implements Serializable {

	/**
	 * generated serial version uid
	 */
	private static final long serialVersionUID = -3184036367245291034L;

	public static final String SELECT_ALL_TALKS = "selectAllTalks";

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min = 1, max = 50)
	private String title;

	private String description;

	@NotNull
	@Column(name = "start_date")
	private Date startDate;

	@NotNull
	@Min(value = 15)
	private Integer duration;

	/**
	 * Default constructor
	 */
	public Talk() {
		super();
	}

	/**
	 * @param title
	 * @param description
	 * @param startDate
	 * @param duration
	 */
	public Talk(String title, String description, Date startDate,
			Integer duration) {
		super();

		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.duration = duration;
	}

	/**
	 * @param id
	 * @param title
	 * @param description
	 * @param startDate
	 * @param duration
	 */
	public Talk(Long id, String title, String description, Date startDate,
			Integer duration) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.duration = duration;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
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
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		if (!(obj instanceof Talk)) {
			return false;
		}
		Talk other = (Talk) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (duration == null) {
			if (other.duration != null) {
				return false;
			}
		} else if (!duration.equals(other.duration)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!startDate.equals(other.startDate)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
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
		return "Talk [id=" + id + ", title=" + title + ", description="
				+ description + ", startDate=" + startDate + ", duration="
				+ duration + "]";
	}

}
