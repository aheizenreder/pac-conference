package com.prodyna.pac.conference.conference.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.prodyna.pac.conference.location.model.Location;

/**
 * Entity for Conference.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = Conference.FIND_CONFERENCES_FOR_LOCATION, query = "SELECT c FROM Conference c WHERE c.location.id = :locationId"),
		@NamedQuery(name = Conference.SELECT_ALL_CONFERENCES, query = "SELECT c FROM Conference c"),
		@NamedQuery(name = Conference.FIND_CONFERENCE_BY_START_DATE, query = "SELECT c FROM Conference c WHERE c.startDate >= :startDate"),
@NamedQuery(name = Conference.FIND_CONFERENCE_BY_START_DATE_AND_LOCATION, query = "SELECT c FROM Conference c WHERE c.location.id = :locationId AND c.startDate >= :startDate") })
@Table(name = "conference_conference", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Conference implements Serializable {

	private static final long serialVersionUID = 9112499508483229069L;

	public static final String SELECT_ALL_CONFERENCES = "selectAllConferences";
	public static final String FIND_CONFERENCES_FOR_LOCATION = "findConferencesForLocation";
	public static final String FIND_CONFERENCES_FOR_LOCATION_PARAM_NAME_LOCATION_ID = "locationId";
	public static final String FIND_CONFERENCE_BY_START_DATE = "findConferenceByStartDate";
	public static final String FIND_CONFERENCE_BY_START_DATE_PARAM_NAME_START_DATE = "startDate";
	public static final String FIND_CONFERENCE_BY_START_DATE_AND_LOCATION = "findConferenceByStartDateAndLocation";
	public static final String FIND_CONFERENCE_BY_START_DATE_AND_LOCATION_PARAM_NAME_START_DATE = "startDate";
	public static final String FIND_CONFERENCE_BY_START_DATE_AND_LOCATION_PARAM_NAME_LOCATION = "locationId";

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
	@Column(name = "end_date")
	private Date endDate;

	@ManyToOne
	@JoinColumn(name = "location_id", referencedColumnName = "id")
	private Location location;

	public Conference() {
		super();
	}

	public Conference(String title, String description, Date startDate,
			Date endDate, Location location) {
		super();

		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
	}

	public Conference(Long id, String title, String description,
			Date startDate, Date endDate, Location location) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
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
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		if (!(obj instanceof Conference)) {
			return false;
		}
		Conference other = (Conference) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (endDate == null) {
			if (other.endDate != null) {
				return false;
			}
		} else if (!endDate.equals(other.endDate)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Conference [id=" + id + ", title=" + title + ", description="
				+ description + ", startDate=" + startDate + ", endDate="
				+ endDate + ", location=" + location + "]";
	}
	
}
