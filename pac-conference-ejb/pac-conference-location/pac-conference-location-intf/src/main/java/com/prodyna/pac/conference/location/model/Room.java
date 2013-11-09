package com.prodyna.pac.conference.location.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity Room represents a room in a conference location.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@Entity
@NamedQuery(name = Room.FIND_ROOMS_FOR_LOCATION, query = "SELECT r FROM Room r WHERE r.location.id = :locationId")
@Table(name = "location_room", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Room implements Serializable {

	public static final String FIND_ROOMS_FOR_LOCATION = "findRoomsForLocation";

	/**
	 * 
	 */
	private static final long serialVersionUID = 796975035282448401L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min = 1, max = 50)
	private String name;

	private String description;

	@NotNull
	@Min(value = 1)
	private Integer capacity;

	@ManyToOne
	@JoinColumn(name = "location_id", referencedColumnName = "id")
	private Location location;

	public Room() {
		super();
	}

	public Room(Location location, String name, String description,
			Integer capacity) {
		super();

		this.location = location;
		this.name = name;
		this.description = description;
		this.capacity = capacity;
	}

	public Room(Location location, Long id, String name, String description,
			Integer capacity) {
		super();

		this.location = location;
		this.id = id;
		this.name = name;
		this.description = description;
		this.capacity = capacity;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the capacity
	 */
	public Integer getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity
	 *            the capacity to set
	 */
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
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

}
