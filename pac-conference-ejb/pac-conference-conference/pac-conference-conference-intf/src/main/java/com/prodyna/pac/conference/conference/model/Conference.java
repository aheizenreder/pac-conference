package com.prodyna.pac.conference.conference.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@NamedQuery(name = Conference.FIND_CONFERENCES_FOR_LOCATION, query = "SELECT c FROM Conference c WHERE c.location.id = :locationId")
@Table(name = "conference_conference", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Conference implements Serializable {

	private static final long serialVersionUID = 9112499508483229069L;

	public static final String FIND_CONFERENCES_FOR_LOCATION = "findConferencesForLocation";
	public static final String FIND_CONFERENCES_FOR_LOCATION_PARAM_NAME_LOCATION_ID = "locationId";

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min = 1, max = 50)
	private String title;

	private String description;

	@NotNull
	@Column(name = "start_date")
	Date startDate;

	@NotNull
	@Column(name = "end_date")
	Date endDate;

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
	 * @param id the id to set
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
	 * @param title the title to set
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
	 * @param description the description to set
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
	 * @param startDate the startDate to set
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
	 * @param endDate the endDate to set
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
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	
}
