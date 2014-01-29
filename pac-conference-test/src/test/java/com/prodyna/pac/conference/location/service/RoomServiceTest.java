/**
 * 
 */
package com.prodyna.pac.conference.location.service;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

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

import com.prodyna.pac.conference.TestArchiveCreator;
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

		room = new Room(location, "778", "Test room for create test.",
				Integer.valueOf(10));
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
		log.info("test update room ...");

		String newName = "New room name";
		String newDescription = "New description";
		Integer newCapacity = 20;

		initTest();
		roomService.create(room);
		log.info("update room with new values ...");
		room.setName(newName);
		room.setDescription(newDescription);
		room.setCapacity(newCapacity);

		roomService.update(room);
		log.info("read room after update ...");
		Room aUp = roomService.get(room.getId());

		cleanUpTest();

		Assert.assertNotNull(aUp.getId());
		Assert.assertEquals(newName, aUp.getName());
		Assert.assertEquals(newDescription, aUp.getDescription());
		Assert.assertEquals(newCapacity, aUp.getCapacity());

		log.info("END testUpdate().");
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.location.service.RoomService#delete(com.prodyna.pac.conference.location.model.Room)}
	 * .
	 */
	@Test
	public void testDelete() {
		log.info("test delete room ...");
		initTest();
		roomService.create(room);

		Long roomId = room.getId();

		Assert.assertNotNull(roomId);

		log.info("delete room with id " + roomId);

		roomService.delete(room);

		log.info("try to get deleted room with id " + roomId);
		Room deleted = roomService.get(roomId);
		Assert.assertNull(deleted);

		log.info("END testDelete().");
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.location.service.RoomService#findRoomByLocation(com.prodyna.pac.conference.location.model.Location)}
	 * .
	 */
	@Test
	public void testFindRoomByLocation() {
		log.info("test find room by location ...");
		initTest();
		// a location is created an a room initialized
		roomService.create(room);
		Assert.assertNotNull(room.getId());

		// create more rooms
		Room room2 = new Room(location, "Room2",
				"Room2 for find by location test", 100);
		roomService.create(room2);
		Assert.assertNotNull(room2.getId());

		Room room3 = new Room(location, "Room3",
				"Room3 for find by location test", 100);
		roomService.create(room3);
		Assert.assertNotNull(room3.getId());

		Room room4 = new Room(location, "Room4",
				"Room4 for find by location test", 100);
		roomService.create(room4);
		Assert.assertNotNull(room4.getId());

		log.info("try to get rooms by location with id " + location.getId());
		List<Room> roomByLocationList = roomService
				.findRoomByLocation(location);
		Assert.assertNotNull(roomByLocationList);
		log.info("number of rooms in location " + roomByLocationList.size());
		Assert.assertEquals(4, roomByLocationList.size());

		log.info("remove a room with id " + room2.getId());
		roomService.delete(room2);

		roomByLocationList = roomService.findRoomByLocation(location);
		Assert.assertNotNull(roomByLocationList);
		log.info("number of rooms in location " + roomByLocationList.size());
		Assert.assertEquals(3, roomByLocationList.size());

		roomService.delete(room3);
		roomService.delete(room4);

		cleanUpTest();
		log.info("END testFindRoomByLocation().");
	}

	@Test
	public void testGetAll() {
		log.info("test select all rooms ...");

		String roomName = "Room";
		String roomDescription = "A description for Room";
		Integer roomCapacity = 10;

		List<Room> testRoomList = new ArrayList<Room>();

		List<Room> existingRoomsList = roomService.getAll();
		int numExistingRooms = existingRoomsList.size();

		int numOfRooms = 5;

		for (int i = 1; i <= numOfRooms; i++) {
			log.info("add test room " + i);
			Room testRoom = new Room(location, roomName + i,
					roomDescription + 1, roomCapacity + i);
			roomService.create(testRoom);
			Assert.assertNotNull(testRoom.getId());
			testRoomList.add(testRoom);
		}

		log.info("select all rooms ...");
		List<Room> allRoomList = roomService.getAll();
		Assert.assertEquals(numOfRooms + numExistingRooms, allRoomList.size());

		log.info("delete all created rooms ...");
		for (Room rom : testRoomList) {
			roomService.delete(rom);
		}

		allRoomList = roomService.getAll();
		Assert.assertEquals(numExistingRooms, allRoomList.size());

	}
}
