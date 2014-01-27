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
import com.prodyna.pac.conference.talk.service.exception.SpeakerNotAvailableException;
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

	@Test(expected = WrongLocationException.class)
	public void testWrongLocationException() throws WrongLocationException {
		Location otherLocation = new Location("Test Location",
				"Test location for WrongLoactionException test", "Test Street",
				"1", "Test City", "12345", "Test Country");
		locationService.create(otherLocation);

		Room testRoom = new Room(otherLocation, "Test Room 1",
				"A test room for WrongLocationException", 300);
		roomService.create(testRoom);

		// try to assing a room with other location than talk
		try {
			talkService.assignRoom(talk, testRoom);
		} catch (WrongLocationException e) {
			log.error("Expected WrongLocationException was thrown: " + e);
			Assert.assertNotNull(e.getExpectedLocation());
			throw e;
		} catch (OccupiedRoomException e) {
			log.error("Room is already occupied! " + e);
		} finally {
			// clean up after test.
			if (testRoom != null && testRoom.getId() != null) {
				roomService.delete(testRoom);
			}
			if (otherLocation != null && otherLocation.getId() != null) {
				locationService.delete(otherLocation);
			}
		}
	}

	@Test(expected = OccupiedRoomException.class)
	public void testOccupiedRoomException() throws OccupiedRoomException {

		// assign room to talk
		try {
			talkService.assignRoom(talk, room);
		} catch (WrongLocationException e) {
			log.error("Unexpected WrongLocationException: " + e);
			Assert.assertNull(e);
		} catch (OccupiedRoomException e) {
			log.error("Unexpected OccupiedRoomException: " + e);
			Assert.assertNull(e);
		}

		// create new Talk
		Talk otherTalk = new Talk("Test Talk",
				"Test talk for test OccupiedRoomException.",
				talk.getStartDate(), talk.getDuration() + 30, conference);
		talkService.create(otherTalk);

		Assert.assertNotNull("Id for other talk is null!", otherTalk.getId());

		// try to assign room to otherTalk
		try {
			talkService.assignRoom(otherTalk, room);
		} catch (WrongLocationException e) {
			log.error("Unexpected WrongLocationException: " + e);
			Assert.assertNull(e);
		} catch (OccupiedRoomException e) {
			log.info("Expected OccupiedRoomException was thrown: " + e);
			Assert.assertNotNull("Collision talk is null!", e.getOccupyTalk());
			Assert.assertNotNull("Occupy task start date is null!",
					e.getStartDate());
			Assert.assertNotNull("Occupy talk end date is null!",
					e.getEndDate());
			throw e;
		} finally {
			if (otherTalk != null && otherTalk.getId() != null) {
				talkService.delete(otherTalk);
			}
			talkService.unassignRoom(talk, room);
		}
	}

	@Test
	public void testAssignUnassignSpeaker() {
		Exception notExpectedException = null;

		Assert.assertNotNull(talk);
		Assert.assertNotNull("Talk id is null!", talk.getId());

		Assert.assertNotNull(speaker);
		Assert.assertNotNull("Speaker id is null!", speaker.getId());

		// assign speaker to talk
		try {
			talkService.assignSpeaker(talk, speaker);
		} catch (SpeakerNotAvailableException e) {
			notExpectedException = e;
			log.error("Unexpected exception: " + e);
		}

		talkService.unassignSpeaker(talk, speaker);

		Assert.assertNull(notExpectedException);
	}

	@Test(expected = SpeakerNotAvailableException.class)
	public void testSpeakerNotAvailableException()
			throws SpeakerNotAvailableException {

		try {
			talkService.assignSpeaker(talk, speaker);
		} catch (SpeakerNotAvailableException e) {
			log.error("Unexpected SpeakerNotAvailableException: " + e);
			Assert.assertNotNull(e);
		}

		Talk otherTalk = new Talk("Test Talk",
				"Test talk for test SpeakerNotAvailableException.",
				talk.getStartDate(), talk.getDuration() + 30, conference);
		talkService.create(otherTalk);

		try {
			talkService.assignSpeaker(otherTalk, speaker);
		} catch (SpeakerNotAvailableException e) {
			log.info("Expected SpeakerNotAvailableException was thrown: " + e);
			Assert.assertNotNull("Blocking talk is null!", e.getTalk());
			Assert.assertNotNull(
					"Start date and time of blocking talk is null!",
					e.getStartDate());
			Assert.assertNotNull("End date and time of blocking talk is null!",
					e.getEndDate());
			throw e;
		} finally {
			if (otherTalk != null && otherTalk.getId() != null) {
				talkService.delete(otherTalk);
			}
			talkService.unassignSpeaker(talk, speaker);
		}
	}
}
