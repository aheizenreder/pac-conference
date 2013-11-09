/**
 * 
 */
package com.prodyna.pac.conference.location.service;

import static org.junit.Assert.fail;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import com.prodyna.pac.conference.location.model.Location;
import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.util.Resources;

/**
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@RunWith(Arquillian.class)
public class RoomServiceTest {

	@Inject
	private Logger log;

	@Inject
	private LocationService locationService;

	@Inject
	private RoomService roomService;

	private Location location;

	private Room room;

	@Deployment
	public static Archive<?> createDeployment() {

		WebArchive war = ShrinkWrap.create(WebArchive.class,
				"pac-conference-location.war");
		war.addClasses(Location.class, LocationService.class,
				LocationServiceImpl.class, Room.class, RoomService.class,
				RoomServiceImpl.class, Resources.class);
		war.addAsResource("META-INF/test-persistence.xml",
				"META-INF/persistence.xml");
		war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		return war;
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

	}

	/**
	 * 
	 */
	private void initTest() {
		log.info("create location for tests ...");
		location = new Location("Test Location", "Location for room tests.",
				"Test street", "12", "Testcity", "12345", "Test Country");

		locationService.create(location);

		log.info("location for tests created with id " + location.getId() + ".");

		room = new Room(location, "778", "Test room for create test.", 10);
	}

	/**
	 * 
	 */
	private void cleanUpTest() {
		log.info("delete test room " + room.getId() + " ...");
		roomService.delete(room);
		log.info("test room deleted.");

		log.info("delete location for tests ...");
		locationService.delete(location);
		log.info("test location deleted.");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.location.service.RoomService#create(com.prodyna.pac.conference.location.model.Room)}
	 * .
	 */
	@Test
	public void testCreate() {
		log.info("create a room ...");
		initTest();
		try {
			Room create = roomService.create(room);
			log.info("Room created with id " + create.getId());
			cleanUpTest();
		} catch (Exception e) {
			fail("Unexpected exception " + e);
		}
		log.info("END testCreate().");
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.location.service.RoomService#get(java.lang.Long)}
	 * .
	 */
	@Test
	public void testGet() {
		log.info("get room with it id ...");
		log.info("create a room for the first..");
		initTest();
		roomService.create(room);
		Long roomId = room.getId();

		Assert.assertNotNull("Room id is null", roomId);

		Room read = roomService.get(roomId);
		Assert.assertNotNull("Room id is null", read.getId());
		cleanUpTest();
	}

	@Test
	public void testGetNotExisting() {
		log.info("get room with a not existing id ...");
		Room read = roomService.get(Long.valueOf(9999));
		Assert.assertNull("No room exists for requested id!", read);
		log.info("END testGetNotExisting().");
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.location.service.RoomService#update(com.prodyna.pac.conference.location.model.Room)}
	 * .
	 */
	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.location.service.RoomService#delete(com.prodyna.pac.conference.location.model.Room)}
	 * .
	 */
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.location.service.RoomService#findRoomByLocation(com.prodyna.pac.conference.location.model.Location)}
	 * .
	 */
	@Test
	public void testFindRoomByLocation() {
		fail("Not yet implemented");
	}

}
