package com.prodyna.pac.conference.location.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity Location represents a location with name and address for conferences.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@Entity
@Table(name = "location_location", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Location implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7112989899489583203L;

	/**
	 * key value for location. The value will automatically generate by
	 * database.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * represents the name of the location. It may not be empty.
	 */
	@NotNull
	@Size(min = 1, max = 50)
	private String name;

	/**
	 * a optional description of a location.
	 */
	private String description;

	/**
	 * represents street for location. It may not be empty.
	 */
	@NotNull
	@Size(min = 1, max = 50)
	private String street;

	/**
	 * house number of locations address. The value for house number may contain
	 * numbers and letters.
	 */
	@NotNull
	@Size(min = 1, max = 10)
	@Column(name = "house_number")
	private String houseNumber;

	/**
	 * city of location. The value for city may not be empty.
	 */
	@NotNull
	@Size(min = 1, max = 50)
	private String city;

	/**
	 * postalcode for location. The value for postalcode may not be empty.
	 */
	@NotNull
	@Size(min = 1, max = 10)
	private String postalcode;

	/**
	 * a optional value for locations country.
	 */
	private String country;

	public Location() {
		super();
	}

	public Location(String name, String description, String street,
			String houseNumber, String city, String postalcode, String country) {
		super();

		this.name = name;
		this.description = description;
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
		this.postalcode = postalcode;
		this.country = country;
	}

	public Location(Long id, String name, String description, String street,
			String houseNumber, String city, String postalcode, String country) {
		super();
		
		this.id = id;
		this.name = name;
		this.description = description;
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
		this.postalcode = postalcode;
		this.country = country;
	}

	/**
	 * get name of location.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * set new value for name of location.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get description for location.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * sets new value for location description.
	 * 
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * get the current value for locations street.
	 * 
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * set new value for locations street.
	 * 
	 * @param street
	 *            the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * get the current value for location house number.
	 * 
	 * @return the houseNumber
	 */
	public String getHouseNumber() {
		return houseNumber;
	}

	/**
	 * set new value for locations house number.
	 * 
	 * @param houseNumber
	 *            the house_number to set
	 */
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	/**
	 * get the current value for locations city.
	 * 
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * set new value for locations city.
	 * 
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * get current value for locations postalcode.
	 * 
	 * @return the postalcode
	 */
	public String getPostalcode() {
		return postalcode;
	}

	/**
	 * set new value for locations postalcode.
	 * 
	 * @param postalcode
	 *            the postalcode to set
	 */
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	/**
	 * get the current value for locations country.
	 * 
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * set new value for locations country.
	 * 
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * get the id of the location.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * set new value for locations id.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
