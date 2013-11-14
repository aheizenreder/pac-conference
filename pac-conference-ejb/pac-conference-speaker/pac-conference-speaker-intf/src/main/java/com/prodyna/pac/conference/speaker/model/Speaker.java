/**
 * 
 */
package com.prodyna.pac.conference.speaker.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Entity Speaker represents speaker for conference talks.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = Speaker.SELECT_ALL_SPEAKER, query = "SELECT s FROM Speaker s") })
@Table(name = "speaker_speaker", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Speaker implements Serializable {

	public static final String SELECT_ALL_SPEAKER = "selectAllSpeaker";

	private static final long serialVersionUID = -7543272403424982190L;

	@Id
	@GeneratedValue
	Long id;

	@NotNull
	@Size(min = 1, max = 50)
	@Pattern(regexp = "[A-Z][a-zA-Z -]+", message = "Invalid name!")
	private String name;

	@NotNull
	@Size(min = 1)
	private String description;

	
	/**
	 * 
	 */
	public Speaker() {
		super();
	}

	/**
	 * @param name
	 * @param description
	 */
	public Speaker(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	/**
	 * @param id
	 * @param name
	 * @param description
	 */
	public Speaker(Long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (!(obj instanceof Speaker)) {
			return false;
		}
		Speaker other = (Speaker) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
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
		return "Speaker [id=" + id + ", name=" + name + ", description="
				+ description + "]";
	}

}
