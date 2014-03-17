/**
 * 
 */
package com.prodyna.pac.conference.rest;

import java.net.URL;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.util.GenericType;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.conference.TestArchiveCreator;
import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.location.model.Location;
import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.rest.admin.conference.ConferenceAdminRestService;
import com.prodyna.pac.conference.rest.admin.location.LocationAdminRestService;
import com.prodyna.pac.conference.rest.admin.location.RoomAdminRestService;
import com.prodyna.pac.conference.rest.admin.speaker.SpeakerAdminRestService;
import com.prodyna.pac.conference.rest.admin.talk.TalkAdminRestService;
import com.prodyna.pac.conference.rest.pub.conference.ConferencePublicRestService;
import com.prodyna.pac.conference.rest.pub.location.LocationPublicRestService;
import com.prodyna.pac.conference.rest.pub.location.RoomPublicRestService;
import com.prodyna.pac.conference.rest.pub.speaker.SpeakerPublicRestService;
import com.prodyna.pac.conference.rest.pub.talk.TalkPublicRestService;
import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.talk.model.Talk;

/**
 * JUnit test for REST interface services
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@RunWith(Arquillian.class)
@RunAsClient
public class ConferenceRestServicesTest {

	private static Logger log = LoggerFactory
			.getLogger(ConferenceRestServicesTest.class);

	/**
	 * prefix for rest resources
	 */
	private static final String RESOURCE_PREFIX = JaxRsActivator.class
			.getAnnotation(ApplicationPath.class).value().substring(1);

	/**
	 * Deployment link
	 */
	@ArquillianResource
	private URL deploymentUrl;

	/**
	 * start date for conferences tests
	 */
	private Calendar startDateCalendar;

	/**
	 * end date for conference tests
	 */
	private Calendar endDateCalendar;

	private Location location;

	@Deployment(testable = false)
	public static Archive<?> createDeployment() {

		return TestArchiveCreator.createDeploymentForRest();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
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
		// create start date
		startDateCalendar = Calendar.getInstance();
		startDateCalendar.set(2014, 12, 24, 18, 0);
		startDateCalendar.set(Calendar.MILLISECOND, 0);
		// create end date
		endDateCalendar = Calendar.getInstance();
		endDateCalendar.set(2014, 12, 26, 20, 0);
		endDateCalendar.set(Calendar.MILLISECOND, 0);
		// create location
		location = new Location("Rest locaiton", "Location for REST tests.",
				"Teststreet", "1a", "Test City", "12345", "Test Country");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConferenceRestService() {
		String publicServiceURL = deploymentUrl.toString() + RESOURCE_PREFIX
				+ "/public/conference";
		String adminServiceURL = deploymentUrl.toString() + RESOURCE_PREFIX
				+ "/admin/conference";

		System.out.println("Conference public rest service URL: "
				+ publicServiceURL);

		ConferencePublicRestService conferencePublicService = ProxyFactory
				.create(ConferencePublicRestService.class, publicServiceURL);

		ConferenceAdminRestService conferenceAdminService = ProxyFactory
				.create(ConferenceAdminRestService.class, adminServiceURL);

		ClientResponse<?> response;
		Location newLocation = createLocation();

		// test create a conference
		Conference conference = new Conference("Rest Conference",
				"Conference for Rest Test", startDateCalendar.getTime(),
				endDateCalendar.getTime(), newLocation);

		response = (ClientResponse<?>) conferenceAdminService
				.create(conference);
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());

		Conference newConference = response.getEntity(Conference.class);
		Assert.assertNotNull(newConference.getId());

		// Test getAll operation
		response = (ClientResponse<?>) conferencePublicService.getAll();

		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		System.out.println("Response: " + response + " status "
				+ response.getStatus());
		List<Conference> conferenceList = (List<Conference>) response
				.getEntity(new GenericType<List<Conference>>() {
				});
		Assert.assertNotNull(conferenceList);
		Assert.assertFalse("conference list may not be empty!",
				conferenceList.isEmpty());
		Assert.assertTrue(
				"Atleast one conference has to be in the conference list!",
				conferenceList.size() >= 1);

		System.out.println("first conference in the list: "
				+ conferenceList.get(0).toString());

		// Test get operation
		response = (ClientResponse<?>) conferencePublicService
				.getById(newConference.getId());
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		Assert.assertEquals("Wrong response by getById()!", newConference,
				response.getEntity(Conference.class));

		// test update operation
		newConference.setTitle("Updated Title");
		response = (ClientResponse<?>) conferenceAdminService
				.update(newConference);
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		Assert.assertEquals("Wrong response from update operation!",
				newConference, response.getEntity(Conference.class));

		// test delete operation
		// store the id of newConference
		Long newConferenceId = newConference.getId();
		response = (ClientResponse<?>) conferenceAdminService
				.delete(newConference.getId());
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		// try to get deleted conference
		response = (ClientResponse<?>) conferencePublicService
				.getById(newConferenceId);
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());

		// delete location
		deleteLocation(newLocation);
	}

	@Test
	public void testLocationRestService() {
		String publicServiceURL = deploymentUrl.toString() + RESOURCE_PREFIX
				+ "/public/location";
		String adminServiceURL = deploymentUrl.toString() + RESOURCE_PREFIX
				+ "/admin/location";

		LocationPublicRestService locationPublicService = ProxyFactory.create(
				LocationPublicRestService.class, publicServiceURL);

		LocationAdminRestService locationAdminService = ProxyFactory.create(
				LocationAdminRestService.class, adminServiceURL);

		// create a Location
		Location location = new Location("Rest locaiton",
				"Location for REST tests.", "Teststreet", "1a", "Test City",
				"12345", "Test Country");
		ClientResponse<?> response = (ClientResponse<?>) locationAdminService
				.create(location);

		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		Location newLocation = response.getEntity(Location.class);
		Assert.assertNotNull(newLocation.getId());
		long locationId = newLocation.getId();

		response.releaseConnection();

		// test getById
		response = (ClientResponse<?>) locationPublicService
				.getById(locationId);

		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		Location readLocation = response.getEntity(Location.class);
		Assert.assertEquals(newLocation, readLocation);

		// test update
		readLocation.setDescription("New Description");
		response = (ClientResponse<?>) locationAdminService
				.update(readLocation);

		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		newLocation = response.getEntity(Location.class);
		Assert.assertEquals(readLocation.getDescription(),
				newLocation.getDescription());

		// delete location
		response = (ClientResponse<?>) locationAdminService.delete(locationId);
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());

		// try to get deleted Location
		response = (ClientResponse<?>) locationPublicService
				.getById(locationId);
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());

	}

	@Test
	public void testRoomService() {

		String publicServiceURL = deploymentUrl.toString() + RESOURCE_PREFIX
				+ "/public/room";
		String adminServiceURL = deploymentUrl.toString() + RESOURCE_PREFIX
				+ "/admin/room";

		int roomListSize = 0;

		RoomPublicRestService roomPublicService = ProxyFactory.create(
				RoomPublicRestService.class, publicServiceURL);

		RoomAdminRestService roomAdminService = ProxyFactory.create(
				RoomAdminRestService.class, adminServiceURL);

		// create location for room test
		Location newLocation = createLocation();

		Room room = new Room(newLocation, "Test Room",
				"Test Room for rest service Test", 100);

		ClientResponse<?> response = (ClientResponse<?>) roomAdminService
				.create(room);
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		Room newRoom = response.getEntity(Room.class);
		Assert.assertNotNull(newRoom.getId());

		// test get room by id
		response = (ClientResponse<?>) roomPublicService.getById(newRoom
				.getId());
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		Assert.assertEquals("Rooms with same id are not equals!", newRoom,
				response.getEntity(Room.class));

		// test get all rooms
		response = (ClientResponse<?>) roomPublicService.getAll();
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		List<Room> roomList = (List<Room>) response
				.getEntity(new GenericType<List<Room>>() {
				});
		Assert.assertNotNull("room list is null!", roomList);
		Assert.assertFalse("Room list may not be empty!", roomList.isEmpty());
		roomListSize = roomList.size();
		Assert.assertTrue("Room list should contain atleast one entry!",
				roomListSize >= 1);

		// Test update operation
		newRoom.setName("Updated Name");
		response = (ClientResponse<?>) roomAdminService.update(newRoom);
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		Assert.assertEquals(newRoom, response.getEntity(Room.class));

		// Test delete operation
		long newRoomId = newRoom.getId();
		response = (ClientResponse<?>) roomAdminService.delete(newRoomId);
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		Assert.assertNotNull(response.getEntity(Room.class));
		// try to get deleted room
		response = (ClientResponse<?>) roomPublicService.getById(newRoomId);
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());

		// delete location after test.
		deleteLocation(newLocation);
	}

	@Test
	public void testSpeakerService() {

		String publicServiceURL = deploymentUrl.toString() + RESOURCE_PREFIX
				+ "/public/speaker";
		String adminServiceURL = deploymentUrl.toString() + RESOURCE_PREFIX
				+ "/admin/speaker";

		int speakerListSize = 0;

		SpeakerPublicRestService speakerPublicService = ProxyFactory.create(
				SpeakerPublicRestService.class, publicServiceURL);

		SpeakerAdminRestService speakerAdminService = ProxyFactory.create(
				SpeakerAdminRestService.class, adminServiceURL);

		Speaker speaker = new Speaker("Test Speaker", "A Speaker for Rest test");

		ClientResponse<?> response = (ClientResponse<?>) speakerAdminService
				.create(speaker);
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		Speaker newSpeaker = response.getEntity(Speaker.class);
		Assert.assertNotNull(newSpeaker.getId());

		// test get speaker by id
		response = (ClientResponse<?>) speakerPublicService.getById(newSpeaker
				.getId());
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		Assert.assertEquals("Speakers with same id are not equals!",
				newSpeaker, response.getEntity(Speaker.class));

		// test get all Speaker
		response = (ClientResponse<?>) speakerPublicService.getAll();
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		List<Speaker> speakerList = (List<Speaker>) response
				.getEntity(new GenericType<List<Speaker>>(){});
		Assert.assertNotNull("speaker list is null!", speakerList);
		Assert.assertFalse("Speaker list may not be empty!",
				speakerList.isEmpty());
		speakerListSize = speakerList.size();
		Assert.assertTrue("Speaker list should contain atleast one entry!",
				speakerListSize >= 1);

		// Test update operation
		newSpeaker.setName("Updated Speaker");
		response = (ClientResponse<?>) speakerAdminService.update(newSpeaker);
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		Assert.assertEquals(newSpeaker, response.getEntity(Speaker.class));

		// Test delete operation
		long newSpeakerId = newSpeaker.getId();
		response = (ClientResponse<?>) speakerAdminService.delete(newSpeakerId);
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		Assert.assertNotNull(response.getEntity(Room.class));
		// try to get deleted speaker
		response = (ClientResponse<?>) speakerPublicService
				.getById(newSpeakerId);
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
	}

	@Test
	public void testTalkRestService() {
		String publicServiceURL = deploymentUrl.toString() + RESOURCE_PREFIX
				+ "/public/talk";
		String adminServiceURL = deploymentUrl.toString() + RESOURCE_PREFIX
				+ "/admin/talk";

		TalkPublicRestService talkPublicService = ProxyFactory.create(
				TalkPublicRestService.class, publicServiceURL);

		TalkAdminRestService talkAdminService = ProxyFactory.create(
				TalkAdminRestService.class, adminServiceURL);

		Calendar talkStartCalendar = Calendar.getInstance();
		talkStartCalendar.setTimeInMillis(0);
		talkStartCalendar.set(2014, 12, 25, 10, 0, 0);

		// create a test conference for talk
		Conference conference = createConference();

		Talk talk = new Talk("Test Rest Talk", "A talk for rest tests",
				talkStartCalendar.getTime(), 120, conference);

		ClientResponse<?> response = (ClientResponse<?>) talkAdminService
				.create(talk);
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		Talk newTalk = response.getEntity(Talk.class);
		Assert.assertNotNull(newTalk.getId());

		// test get talk by id
		response = (ClientResponse<?>) talkPublicService.getById(newTalk
				.getId());
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		Talk readedTalk = response.getEntity(Talk.class);
		Assert.assertEquals("Talks with same id are not equal!", newTalk,
				readedTalk);

		// test get all talks
		response = (ClientResponse<?>) talkPublicService.getAll();
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		List<Talk> talkList = (List<Talk>) response.getEntity(new GenericType<List<Talk>>(){});
		Assert.assertNotNull("Talk list after getAll() may not be null!",
				talkList);
		Assert.assertFalse("Talk list may not be empty!", talkList.isEmpty());
		Assert.assertTrue("Talk list should contain atleast one entry!",
				talkList.size() >= 1);

		// test delete talk
		response = (ClientResponse<?>) talkAdminService.delete(newTalk.getId());
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());

		// delete conference
		deleteConference(conference);
		// delete location
		deleteLocation(location);
	}

	/**
	 * helper method to create a location.
	 */
	private Location createLocation() {

		String locationAdminServiceURL = deploymentUrl.toString()
				+ RESOURCE_PREFIX + "/admin/location";

		LocationAdminRestService locationAdminService = ProxyFactory.create(
				LocationAdminRestService.class, locationAdminServiceURL);

		ClientResponse<?> response = (ClientResponse<?>) locationAdminService
				.create(location);

		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		Location newLocation = response.getEntity(Location.class);
		Assert.assertNotNull(newLocation.getId());
		return newLocation;
	}

	/**
	 * helper method to delete a location.
	 * 
	 * @param location
	 *            instance of location which is to delete.
	 * @return a instance of location after persist trough service.
	 */
	private void deleteLocation(Location location) {

		String locationAdminServiceURL = deploymentUrl.toString()
				+ RESOURCE_PREFIX + "/admin/location";

		LocationAdminRestService locationAdminService = ProxyFactory.create(
				LocationAdminRestService.class, locationAdminServiceURL);

		ClientResponse<?> response = (ClientResponse<?>) locationAdminService
				.delete(location.getId());

		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());

	}

	/**
	 * Helper function to create a conference for test.
	 * 
	 * @return a conference for test.
	 */
	private Conference createConference() {
		String adminServiceURL = deploymentUrl.toString() + RESOURCE_PREFIX
				+ "/admin/conference";

		ConferenceAdminRestService conferenceAdminService = ProxyFactory
				.create(ConferenceAdminRestService.class, adminServiceURL);

		Location newLocation = createLocation();

		// test create a conference
		Conference conference = new Conference("Rest Conference",
				"Conference for Rest Test", startDateCalendar.getTime(),
				endDateCalendar.getTime(), newLocation);

		ClientResponse<?> response = (ClientResponse<?>) conferenceAdminService
				.create(conference);
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());

		Conference newConference = response.getEntity(Conference.class);
		Assert.assertNotNull(newConference.getId());

		return newConference;
	}

	/**
	 * Helper method to delete a conference provided as parameter.
	 * 
	 * @param conference
	 *            a conference instance to delete.
	 */
	private void deleteConference(Conference conference) {

		String adminServiceURL = deploymentUrl.toString() + RESOURCE_PREFIX
				+ "/admin/conference";

		ConferenceAdminRestService conferenceAdminService = ProxyFactory
				.create(ConferenceAdminRestService.class, adminServiceURL);

		ClientResponse<?> response = (ClientResponse<?>) conferenceAdminService
				.delete(conference.getId());
		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
	}
}
