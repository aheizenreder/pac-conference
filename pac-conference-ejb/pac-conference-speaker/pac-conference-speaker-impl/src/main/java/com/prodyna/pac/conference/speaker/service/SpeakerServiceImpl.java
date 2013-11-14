/**
 * 
 */
package com.prodyna.pac.conference.speaker.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.prodyna.pac.conference.speaker.model.Speaker;

/**
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@Stateless
public class SpeakerServiceImpl implements SpeakerService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.speaker.service.SpeakerService#create(com.
	 * prodyna.pac.conference.speaker.model.Speaker)
	 */
	@Override
	public Speaker create(Speaker speaker) {
		log.info("Create new speaker " + speaker.getName() + " ...");
		em.persist(speaker);
		log.info("Speaker " + speaker.getName() + " persisted with id "
				+ speaker.getId());

		return speaker;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.speaker.service.SpeakerService#get(java.lang
	 * .Long)
	 */
	@Override
	public Speaker get(Long id) {
		Speaker speaker = null;
		log.info("Get speaker by id " + id);
		speaker = em.find(Speaker.class, id);
		if (speaker != null) {
			log.info("Found speaker for id " + id + " speakers name is "
					+ speaker.getName());
		}
		return speaker;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.speaker.service.SpeakerService#getAll()
	 */
	@Override
	public List<Speaker> getAll() {
		log.info("Select all speaker ...");

		Query selectAllSpeakerQuery = em
				.createNamedQuery(Speaker.SELECT_ALL_SPEAKER);
		List<Speaker> resultList = (List<Speaker>) selectAllSpeakerQuery
				.getResultList();
		if (resultList != null) {
			log.info("there are " + resultList.size() + " speakers found.");
		}
		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.speaker.service.SpeakerService#update(com.
	 * prodyna.pac.conference.speaker.model.Speaker)
	 */
	@Override
	public Speaker update(Speaker speaker) {
		log.info("Update speaker with id " + speaker.getId() + " ...");
		em.merge(speaker);
		log.info("Speaker with id " + speaker.getId() + " updated.");

		return speaker;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.speaker.service.SpeakerService#delete(com.
	 * prodyna.pac.conference.speaker.model.Speaker)
	 */
	@Override
	public Speaker delete(Speaker speaker) {
		log.info("Delete speaker with id " + speaker.getId());
		Speaker sp = em.find(Speaker.class, speaker.getId());
		em.remove(sp);
		log.info("Speaker with id " + sp.getId() + " was deleted.");

		return sp;
	}

}
