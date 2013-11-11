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
import org.jboss.arquillian.junit.InSequence;
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
import com.prodyna.pac.conference.util.Resources;

/**
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@RunWith(Arquillian.class)
public class LocationServiceTest {

	@Inject
	private Logger log;

	@Inject
	private LocationService locationService;

	@Deployment
	public static Archive<?> createDeployment() {

		WebArchive war = ShrinkWrap.create(WebArchive.class,
				"pac-conference-location.war");
		war.addClasses(Location.class, LocationService.class,
				LocationServiceImpl.class, Resources.class);
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
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	@InSequence(value = 1)
	public void testCreate() {
		log.info("start test create location ...");
		Location location = new Location("TestLocation1",
				"It is a first location in test.", "Test street", "124a",
				"Testcity", "12345", "Testcountry");
		Location afterCreateLocation = null;
		try {
			afterCreateLocation = locationService.create(location);

			Assert.assertNotNull("Location id may not be null after create!",
					afterCreateLocation.getId());
			Assert.assertEquals("Name of location not equal!",
					location.getName(), afterCreateLocation.getName());
			Assert.assertEquals("Description not equal!",
					location.getDescription(),
					afterCreateLocation.getDescription());
			Assert.assertEquals("Location street is not equal!",
					location.getStreet(), afterCreateLocation.getStreet());
			Assert.assertEquals("Locations house number is not equal!",
					location.getHouseNumber(),
					afterCreateLocation.getHouseNumber());
			Assert.assertEquals("Location city is not equal!",
					location.getCity(), afterCreateLocation.getCity());
			Assert.assertEquals("Locations postalcode is not equal!",
					location.getPostalcode(),
					afterCreateLocation.getPostalcode());
			Assert.assertEquals("Country of location is not equals",
					location.getCountry(), afterCreateLocation.getCountry());

		} catch (Exception e) {
			log.error("Unexpected exception " + e);
			fail("Unexpected exeption " + e);
		}

		log.info("END testCreate().");
	}

	@Test
	@InSequence(value = 2)
	public void testCreateWithNull() {
		log.info("try to add a location without a name ..");
		Location woName = new Location(null, null, "teststreet", "123a",
				"testcity", "123321", null);
		Exception expectedException = null;
		try {
			locationService.create(woName);
		} catch (Exception e) {
			log.info("Expected exception " + e);
			expectedException = e;
		}
		Assert.assertNotNull("A exception was expected!", expectedException);
		log.info("END testCreateWithNull().");
	}

	@Test
	@InSequence(value = 3)
	public void testGet() {
		log.info("test reading a location ...");
		Location loc = new Location("Read", "Location from readtest",
				"readstreet", "1", "Readcity", "12345", null);
		Location create = locationService.create(loc);
		log.info("id from loc instance: " + loc.getId());
		log.info("id from create instance: " + create.getId());
		Location read = locationService.get(create.getId());
		Assert.assertEquals("Id are after read not the same!", create.getId(),
				read.getId());

		log.info("END testGet().");
	}

	@Test
	@InSequence(value = 4)
	public void testGetNotExisting() {
		log.info("test reading not existing id ...");
		Location loc = locationService.get(Long.valueOf(9999));
		log.info("requested location is null: " + loc);
		Assert.assertNull("No location was expected!", loc);

		log.info("END testGetNotExisting().");
	}

	@Test
	@InSequence(value = 5)
	public void testUpdate() {
		log.info("test update location ...");
		String newName = "New location name";
		String newDescritpion = "New descritpion for update location";
		String newStreet = "new street";
		String newHouseNuber = "341";
		String newCity = "Newcity";
		String newPostalcode = "4321";
		String newCountry = "Newcountry";

		Location loc = new Location("Update Location",
				"Location for update Test", "updatestreet", "123",
				"Updatecity", "12345", null);

		Location create = locationService.create(loc);

		Assert.assertNotNull("Id of location may not be null!", create.getId());

		loc.setName(newName);
		loc.setDescription(newDescritpion);
		loc.setStreet(newStreet);
		loc.setHouseNumber(newHouseNuber);
		loc.setCity(newCity);
		loc.setPostalcode(newPostalcode);
		loc.setCountry(newCountry);

		Location update = locationService.update(loc);

		Assert.assertEquals(update.getName(), newName);
		Assert.assertEquals(update.getDescription(), newDescritpion);
		Assert.assertEquals(update.getStreet(), newStreet);
		Assert.assertEquals(update.getHouseNumber(), newHouseNuber);
		Assert.assertEquals(update.getCity(), newCity);
		Assert.assertEquals(update.getPostalcode(), newPostalcode);
		Assert.assertEquals(update.getCountry(), newCountry);

		log.info("END testUpdate().");

	}

	@Test
	@InSequence(value = 6)
	public void testDelete() {
		log.info("test delete location ...");
		Location loc = new Location("Delete Location",
				"Location for delete test", "delete street", "1234c",
				"Delete city", "12345", "Delete country");
		Location delete = locationService.create(loc);

		Assert.assertNotNull(delete.getId());
		Location deleted = locationService.delete(delete);

		// try to get the deleted location
		Location test = locationService.get(deleted.getId());

		Assert.assertNull(test);
		log.info("END testDelete().");
	}

	@Test
	@InSequence(value = 7)
	public void testGetAll() {
		log.info("test select all locations ...");

		String locationName = "Location";
		String locationDescription = "A description for Location";
		String locationStreet = "Teststreet";
		String locationHousenumber = "123a";
		String locationCity = "Testcity";
		String locationPostalcode = "1234";
		List<Location> testLoctionList = new ArrayList<Location>();

		List<Location> existingLocationsList = locationService.getAll();
		int numExistingLocations = existingLocationsList.size();

		int numOfLocations = 5;

		for (int i = 1; i <= 5; i++) {
			log.info("add test locaiton " + i);
			Location testLocation = new Location(locationName + i,
					locationDescription + 1, locationStreet + i,
					locationHousenumber, locationCity + i, locationPostalcode
							+ i, null);
			locationService.create(testLocation);
			Assert.assertNotNull(testLocation.getId());
			testLoctionList.add(testLocation);
		}

		log.info("select all locations ...");
		List<Location> allLocationsList = locationService.getAll();
		Assert.assertEquals(numOfLocations + numExistingLocations,
				allLocationsList.size());

		log.info("delete all created locations ...");
		for (Location loc : testLoctionList) {
			locationService.delete(loc);
		}

		allLocationsList = locationService.getAll();
		Assert.assertEquals(numExistingLocations, allLocationsList.size());
	}
}
