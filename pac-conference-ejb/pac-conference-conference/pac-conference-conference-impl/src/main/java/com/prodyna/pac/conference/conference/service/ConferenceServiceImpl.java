/**
 * 
 */
package com.prodyna.pac.conference.conference.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.location.model.Location;

/**
 * Implementation of ConferenceService.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@Stateless
public class ConferenceServiceImpl implements ConferenceService {

	@Inject
	private Logger log;
	
	@Inject
	private EntityManager em;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.conference.service.ConferenceService#create
	 * (com.prodyna.pac.conference.conference.model.Conference)
	 */
	@Override
	public Conference create(Conference conference) {
		log.info("Create new conference " + conference.getTitle() + " ...");
		em.persist(conference);
		log.info("Conference " + conference.getTitle() + " persisted with id "
				+ conference.getId());

		return conference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.conference.service.ConferenceService#get(java
	 * .lang.Long)
	 */
	@Override
	public Conference get(Long id) {
		Conference conference = null;
		log.info("Get conference by id " + id);
		conference = em.find(Conference.class, id);
		if (conference != null) {
			log.info("Found conference for id " + id + " conference title is "
					+ conference.getTitle());
		}
		return conference;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.conference.conference.service.ConferenceService#getAll()
	 */
	@Override
	public List<Conference> getAll() {
		log.info("Select all conferences ...");

		Query selectAllConferencesQuery = em.createNamedQuery(Conference.SELECT_ALL_CONFERENCES);
		List<Conference> resultList = (List<Conference>) selectAllConferencesQuery
				.getResultList();
		if (resultList != null) {
			log.info("there are " + resultList.size() + " conferences found.");
		}
		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.conference.service.ConferenceService#update
	 * (com.prodyna.pac.conference.conference.model.Conference)
	 */
	@Override
	public Conference update(Conference conference) {
		log.info("Update conference with id " + conference.getId() + " ...");
		em.merge(conference);
		log.info("Conference with id " + conference.getId() + " updated.");

		return conference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.conference.service.ConferenceService#delete
	 * (com.prodyna.pac.conference.conference.model.Conference)
	 */
	@Override
	public Conference delete(Conference conference) {
		log.info("Delete conference with id " + conference.getId());
		Conference conf = em.find(Conference.class, conference.getId());
		em.remove(conf);
		log.info("Conference with id " + conf.getId() + " was deleted.");

		return conf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.conference.service.ConferenceService#
	 * findConferenceByLocation
	 * (com.prodyna.pac.conference.location.model.Location)
	 */
	@Override
	public List<Conference> findConferenceByLocation(Location location) {
		log.info("Find all conferences for a location with id " + location.getId()
				+ " ...");

		Long locationId = location.getId();
		Query findConferencesByLocationQuery = em
				.createNamedQuery(Conference.FIND_CONFERENCES_FOR_LOCATION);
		findConferencesByLocationQuery
				.setParameter(
						Conference.FIND_CONFERENCES_FOR_LOCATION_PARAM_NAME_LOCATION_ID,
						locationId);
		List<Conference> resultList = (List<Conference>) findConferencesByLocationQuery
				.getResultList();
		if (resultList != null) {
			log.info("there are " + resultList.size() + " conferences found.");
		}
		return resultList;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.conference.conference.service.ConferenceService#findConferencesStartedStartedAfterDate(java.util.Date)
	 */
	@Override
	public List<Conference> findConferencesStartedAfterDate(
			Date startDate) {
		log.info("Find all conferences started on or after  " + startDate
				+ " ...");

		Query findConferencesByStartDateQuery = em
				.createNamedQuery(Conference.FIND_CONFERENCE_BY_START_DATE);
		findConferencesByStartDateQuery
				.setParameter(
						Conference.FIND_CONFERENCE_BY_START_DATE_PARAM_NAME_START_DATE,
						startDate);
		List<Conference> resultList = (List<Conference>) findConferencesByStartDateQuery
				.getResultList();
		if (resultList != null) {
			log.info("there are " + resultList.size() + " conferences found.");
		}
		return resultList;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.conference.conference.service.ConferenceService#findConferencesByStartDateLocation(com.prodyna.pac.conference.location.model.Location, java.util.Date)
	 */
	@Override
	public List<Conference> findConferencesByStartDateLocation(
			Location location, Date startDate) {
		log.info("Find all conferences started on or after  " + startDate
				+ " in location "+location.getName()+" ...");

		Query findConferencesByStartDateLocationQuery = em
				.createNamedQuery(Conference.FIND_CONFERENCE_BY_START_DATE_AND_LOCATION);
		findConferencesByStartDateLocationQuery
				.setParameter(
						Conference.FIND_CONFERENCE_BY_START_DATE_AND_LOCATION_PARAM_NAME_START_DATE,
						startDate);
		findConferencesByStartDateLocationQuery
		.setParameter(
				Conference.FIND_CONFERENCE_BY_START_DATE_AND_LOCATION_PARAM_NAME_LOCATION,
				location.getId());
		List<Conference> resultList = (List<Conference>) findConferencesByStartDateLocationQuery
				.getResultList();
		if (resultList != null) {
			log.info("there are " + resultList.size() + " conferences found.");
		}
		return resultList;
	}

}
