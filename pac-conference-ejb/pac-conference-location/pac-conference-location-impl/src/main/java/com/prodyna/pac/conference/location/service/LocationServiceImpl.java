/**
 * 
 */
package com.prodyna.pac.conference.location.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;

import com.prodyna.pac.conference.location.model.Location;
import com.prodyna.pac.conference.monitoring.logging.Logged;
import com.prodyna.pac.conference.monitoring.performance.Monitored;

/**
 * Implementation of LocationService.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@Logged
@Monitored
@Stateless
public class LocationServiceImpl implements LocationService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.location.service.LocationService#create(com
	 * .prodyna.pac.conference.location.model.Location)
	 */
	@Override
	public Location create(Location location) {
		log.info("Create new location " + location.getName() + "...");
		em.persist(location);
		log.info("Location " + location.getName() + "persisted with id "
				+ location.getId());

		return location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.location.service.LocationService#get(java.
	 * lang.Long)
	 */
	@Override
	public Location get(Long id) {
		Location location = null;
		log.info("Get location by its id " + id);
		location = em.find(Location.class, id);
		if (location != null) {
			log.info("Found location for id " + id + ": location name is "
					+ location.getName());
		}

		return location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.location.service.LocationService#getAll()
	 */
	@Override
	public List<Location> getAll() {
		log.info("select all locations ...");

		TypedQuery<Location> selectAllLocationsQuery = em
				.createNamedQuery(Location.SELECT_ALL_LOCATIONS, Location.class);
		List<Location> resultList = selectAllLocationsQuery
				.getResultList();
		if (resultList != null) {
			log.info("there are " + resultList.size() + " locations found.");
		}
		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.location.service.LocationService#update(com
	 * .prodyna.pac.conference.location.model.Location)
	 */
	@Override
	public Location update(Location location) {
		log.info("Update location with id " + location.getId() + " ...");
		em.merge(location);
		log.info("Location with id " + location.getId() + " updated");
		return location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.location.service.LocationService#delete(com
	 * .prodyna.pac.conference.location.model.Location)
	 */
	@Override
	public Location delete(Location location) {
		log.info("Delete location with id " + location.getId());
		Location loc = em.find(Location.class, location.getId());
		em.remove(loc);
		log.info("Location with id " + loc.getId() + " was deleted ...");

		return loc;
	}

}
