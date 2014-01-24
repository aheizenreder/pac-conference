/**
 * 
 */
package com.prodyna.pac.conference.talk.service;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import com.prodyna.pac.conference.TestArchiveCreator;
import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.conference.service.ConferenceService;
import com.prodyna.pac.conference.location.model.Location;
import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.location.service.LocationService;
import com.prodyna.pac.conference.location.service.RoomService;
import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.speaker.service.SpeakerService;
import com.prodyna.pac.conference.talk.model.Talk;
import com.prodyna.pac.conference.talk.service.exception.OccupiedRoomException;
import com.prodyna.pac.conference.talk.service.exception.WrongLocationException;

/**
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@RunWith(Arquillian.class)
public class TalkServiceTest {

	@Inject
	private Logger log;

	@Inject
	private TalkService talkService;

	@Inject
	private SpeakerService speakerService;

	@Inject
	private RoomService roomService;

	@Inject
	private LocationService locationService;

	@Inject
	private ConferenceService conferenceService;

	private Talk talk;

	private Location location;

	private Room room;

	private Speaker speaker;

	private Conference conference;

	@Deployment
	public static Archive<?> createDeployment() {

		return TestArchiveCreator.createDeployment();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// create a speaker
		speaker = new Speaker("Mickey Mouse",
				"A famous speaker from Disney team.");
		speakerService.create(speaker);
		Assert.assertNotNull(speaker.getId());

		// create a location
		location = new Location("Disney Land",
				"A glamour location for all possible events",
				"Disney Land Road", "1", "Paris", "F-12345", "France");
		locationService.create(location);
		Assert.assertNotNull(location.getId());

		// create a room
		room = new Room(location, "Oval Office",
				"Replique of a famous room in White House.", 20);
		roomService.create(room);
		Assert.assertNotNull(room.getId());

		// create conference
		Calendar confStartDateCalendar = Calendar.getInstance();
		confStartDateCalendar.set(Calendar.DAY_OF_MONTH, 31);
		confStartDateCalendar.set(Calendar.MONTH, Calendar.DECEMBER);
		confStartDateCalendar.set(Calendar.HOUR_OF_DAY, 18);
		confStartDateCalendar.set(Calendar.MINUTE, 0);
		confStartDateCalendar.set(Calendar.SECOND, 0);
		confStartDateCalendar.set(Calendar.MILLISECOND, 0);
		Calendar confEndDateCalendar = Calendar.getInstance();
		confEndDateCalendar.set(Calendar.DAY_OF_MONTH, 01);
		confEndDateCalendar.set(Calendar.MONTH, Calendar.JANUARY);
		confEndDateCalendar.set(Calendar.YEAR, 2015);
		confEndDateCalendar.set(Calendar.HOUR_OF_DAY, 12);
		confEndDateCalendar.set(Calendar.MINUTE, 0);
		confEndDateCalendar.set(Calendar.SECOND, 0);
		confEndDateCalendar.set(Calendar.MILLISECOND, 0);
		conference = new Conference("New Year party",
				"The famous New Year party in Disneyland",
				confStartDateCalendar.getTime(), confEndDateCalendar.getTime(),
				location);
		conferenceService.create(conference);
		Assert.assertNotNull(room.getId());

		// create the talk for test
		Calendar talkStartDateCalendar = (Calendar) confStartDateCalendar
				.clone();
		talkStartDateCalendar.set(Calendar.HOUR_OF_DAY, 23);
		talkStartDateCalendar.set(Calendar.MINUTE, 35);
		talk = new Talk(
				"New Year Speach.",
				"New Year Spach of Mickey Mouse the the all the folks in Disneyland",
				talkStartDateCalendar.getTime(), 25, conference);
		talkService.create(talk);
		Assert.assertNotNull(talk.getId());

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		if (talk != null && talk.getId() != null) {
			// delete talk
			talkService.delete(talk);
		}

		if (speaker != null && speaker.getId() != null) {
			// delete speaker
			speakerService.delete(speaker);
		}

		if (room != null && room.getId() != null) {
			// delete room
			roomService.delete(room);
		}

		if (conference != null && conference.getId() != null) {
			// delete conference
			conferenceService.delete(conference);
		}

		if (location != null && location.getId() != null) {
			// delete location
			locationService.delete(location);
		}
	}

	@Test
	public void testCreateTalk() {
		Assert.assertNotNull(talk);
	}

	@Test
	public void testGetTalk() {
		Assert.assertNotNull(talk);
		Assert.assertNotNull("Talk id is null!", talk.getId());

		// get Talk by id
		Long searchId = talk.getId();

		Talk result = talkService.get(searchId);

		// check results
		Assert.assertNotNull("Result talk is null!", result);
		// check on equals
		Assert.assertEquals("Result is not equal to the requested!", talk,
				result);
		log.info("Result of get by id ist talk with id " + result.getId()
				+ " and title " + result.getTitle());
	}

	@Test
	public void testGetAll() {
		Assert.assertNotNull(talk);
		Assert.assertNotNull("Talk id is null!", talk.getId());

		List<Talk> talkList = talkService.getAll();

		// check results
		Assert.assertNotNull("Result talk list is null!", talkList);
		// check list size
		Assert.assertFalse("Result talk list is empty!", talkList.isEmpty());

		log.info("Result of getAll() is a list with size  " + talkList.size());
	}

	@Test
	public void testUpdate() {
		Assert.assertNotNull(talk);
		Assert.assertNotNull("Talk id is null!", talk.getId());

		// get talk id for later request
		Long talkId = talk.getId();

		// set new Title
		talk.setTitle("New Title");

		// update talk
		talkService.update(talk);

		// request updated talk by id
		Talk updatedTalk = talkService.get(talkId);

		Assert.assertNotNull("Talk is null after update!", updatedTalk);
		Assert.assertEquals("Talk title is not expected one!", "New Title",
				updatedTalk.getTitle());
	}

	@Test
	public void testAssignUnassignRoom() {
		Exception notExpectedException = null;

		Assert.assertNotNull(talk);
		Assert.assertNotNull("Talk id is null!", talk.getId());

		Assert.assertNotNull(room);
		Assert.assertNotNull("Room id is null!", room.getId());

		// assign room to talk
		try {
			talkService.assignRoom(talk, room);
		} catch (WrongLocationException e) {
			notExpectedException = e;
			log.error("Unexpected exception: " + e);
		} catch (OccupiedRoomException e) {
			notExpectedException = e;
			log.error("Unexpected exception: " + e);
		}

		talkService.unassignRoom(talk, room);
		
		Assert.assertNull(notExpectedException);
	}
}
