/**
 * 
 */
package com.prodyna.pac.conference.conference.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import com.prodyna.pac.conference.TestArchiveCreator;
import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.location.model.Location;
import com.prodyna.pac.conference.location.service.LocationService;
import com.prodyna.pac.conference.location.service.LocationServiceImpl;
import com.prodyna.pac.conference.util.Resources;

/**
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@RunWith(Arquillian.class)
public class ConferenceServiceTest {

	@Inject
	private Logger log;

	@Inject
	private ConferenceService conferenceService;

	@Inject
	private LocationService locationService;

	private Location location1;

	private Location location2;

	private Date startDate;

	private Date endDate;

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
		log.info("set up test data for tests ...");
		location1 = new Location("Test Location1",
				"Description for Test Location1", "Test street1", "1234a",
				"Testcity1", "12345", null);
		locationService.create(location1);
		Assert.assertNotNull(location1.getId());
		location2 = new Location("Test Location2",
				"Description for Test Location2", "Test street1", "1234a",
				"Testcity2", "54321", null);
		locationService.create(location2);
		Assert.assertNotNull(location2.getId());

		log.info("initialize calendars ...");
		Calendar startDateCal = Calendar.getInstance();
		startDateCal.set(Calendar.MONTH, Calendar.DECEMBER);
		startDateCal.set(Calendar.DAY_OF_MONTH, 24);
		startDateCal.set(Calendar.HOUR_OF_DAY, 9);
		startDateCal.set(Calendar.MINUTE, 0);
		startDateCal.set(Calendar.SECOND, 0);
		startDateCal.set(Calendar.MILLISECOND, 0);
		startDate = startDateCal.getTime();
		log.info("Start date " + startDate);

		Calendar endDateCal = Calendar.getInstance();
		endDateCal.set(Calendar.MONTH, Calendar.DECEMBER);
		endDateCal.set(Calendar.DAY_OF_MONTH, 26);
		endDateCal.set(Calendar.HOUR_OF_DAY, 18);
		endDateCal.set(Calendar.MINUTE, 0);
		endDateCal.set(Calendar.SECOND, 0);
		endDateCal.set(Calendar.MILLISECOND, 0);
		endDate = endDateCal.getTime();
		log.info("End date " + endDate);

		log.info("END setUp().");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		if (location1.getId() != null) {
			locationService.delete(location1);
		}

		if (location2.getId() != null) {
			locationService.delete(location2);
		}
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.conference.service.ConferenceService#create(com.prodyna.pac.conference.conference.model.Conference)}
	 * .
	 */
	@Test
	public void testCreate() {
		log.info("test create conference ...");

		Conference conference = new Conference("Test Conference",
				"Description for Test Conference", startDate, endDate,
				location1);
		conferenceService.create(conference);
		Assert.assertNotNull(conference.getId());

		conferenceService.delete(conference);

		log.info("END testCreate().");
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.conference.service.ConferenceService#get(java.lang.Long)}
	 * .
	 */
	@Test
	public void testGet() {
		log.info("test get conference by id ...");

		Conference conference = new Conference("TestGet Conference",
				"Description for Test Conference", startDate, endDate,
				location1);
		conferenceService.create(conference);
		Assert.assertNotNull(conference.getId());

		Conference read = conferenceService.get(conference.getId());
		Assert.assertNotNull(read);
		Assert.assertEquals(conference.getId(), conference.getId());
		Assert.assertEquals(conference.getTitle(), read.getTitle());
		Assert.assertEquals(conference.getDescription(), read.getDescription());
		Assert.assertEquals(conference.getStartDate(), read.getStartDate());
		Assert.assertEquals(conference.getEndDate(), read.getEndDate());
		Assert.assertEquals(conference.getLocation(), read.getLocation());

		conferenceService.delete(conference);
		log.info("END testGet().");
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.conference.service.ConferenceService#getAll()}
	 * .
	 */
	@Test
	public void testGetAll() {
		log.info("test get all conferences ...");
		List<Conference> existList = conferenceService.getAll();
		Assert.assertNotNull(existList);
		int existListSize = existList.size();
		log.info("number of existing conferences: " + existListSize);

		log.info("add new conference ...");
		Conference conference = new Conference("TestGetAll Conference",
				"Description for TestGetAll Conference", startDate, endDate,
				location1);
		conferenceService.create(conference);
		Assert.assertNotNull(conference.getId());

		List<Conference> newList = conferenceService.getAll();
		int newListSize = newList.size();
		log.info("new list size: " + newListSize);

		Assert.assertEquals(existListSize + 1, newListSize);

		conferenceService.delete(conference);
		log.info("END testGetAll().");
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.conference.service.ConferenceService#update(com.prodyna.pac.conference.conference.model.Conference)}
	 * .
	 */
	@Test
	public void testUpdate() {
		String newTitle = "New Tittle";
		String newDescription = "New Description";
		Calendar newStartDateCal = Calendar.getInstance();
		newStartDateCal.add(Calendar.DAY_OF_MONTH, 7);
		newStartDateCal.set(Calendar.HOUR_OF_DAY, 9);
		newStartDateCal.set(Calendar.MINUTE, 30);
		newStartDateCal.set(Calendar.SECOND, 0);
		newStartDateCal.set(Calendar.MILLISECOND, 0);
		Date newStartDate = newStartDateCal.getTime();

		Calendar newEndDateCal = Calendar.getInstance();
		newEndDateCal.add(Calendar.DAY_OF_MONTH, 10);
		newEndDateCal.set(Calendar.HOUR_OF_DAY, 19);
		newEndDateCal.set(Calendar.MINUTE, 55);
		newEndDateCal.set(Calendar.SECOND, 0);
		newEndDateCal.set(Calendar.MILLISECOND, 0);
		Date newEndDate = newEndDateCal.getTime();

		log.info("test update ...");

		log.info("create new conference for update test ...");
		Conference conference = new Conference("TestUpdate Conference",
				"Description for TestUpdate Conference", startDate, endDate,
				location1);
		conferenceService.create(conference);
		Assert.assertNotNull(conference.getId());

		log.info("update conference ...");
		conference.setTitle(newTitle);
		conference.setDescription(newDescription);
		conference.setStartDate(newStartDate);
		conference.setEndDate(newEndDate);
		conference.setLocation(location2);

		conferenceService.update(conference);

		log.info("read conference from database again ...");
		Conference read = conferenceService.get(conference.getId());

		Assert.assertNotNull(read);

		Assert.assertEquals(newTitle, read.getTitle());
		Assert.assertEquals(newDescription, read.getDescription());
		Assert.assertEquals(newStartDate, read.getStartDate());
		Assert.assertEquals(newEndDate, read.getEndDate());
		Assert.assertEquals(location2, read.getLocation());

		conferenceService.delete(conference);

		log.info("END testUpdate().");
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.conference.service.ConferenceService#delete(com.prodyna.pac.conference.conference.model.Conference)}
	 * .
	 */
	@Test
	public void testDelete() {
		log.info("test delete conference ...");

		log.info("create new conference for update test ...");
		Conference conference = new Conference("TestDelete Conference",
				"Description for TestDelete Conference", startDate, endDate,
				location1);
		conferenceService.create(conference);
		Assert.assertNotNull(conference.getId());

		Long deletedId = conference.getId();

		log.info("delete created conference with id " + deletedId + " ...");
		conferenceService.delete(conference);

		log.info("try to get the conference with id from deleted conference ...");
		Conference deleted = conferenceService.get(deletedId);
		Assert.assertNull(deleted);

		log.info("END testDelete().");
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.conference.service.ConferenceService#findConferenceByLocation(com.prodyna.pac.conference.location.model.Location)}
	 * .
	 */
	@Test
	public void testFindConferenceByLocation() {
		log.info("test get conferences by location ...");

		List<Conference> conferencesLocationList = conferenceService
				.findConferenceByLocation(location1);
		Assert.assertNotNull(conferencesLocationList);
		int numConf = conferencesLocationList.size();

		log.info("create new conference for find by Location test ...");
		Conference conference = new Conference("Test Conference",
				"Description for Test Conference", startDate, endDate,
				location1);
		conferenceService.create(conference);
		Assert.assertNotNull(conference.getId());

		log.info("get all conferences for location " + location1.getName());
		List<Conference> updatedList = conferenceService
				.findConferenceByLocation(location1);
		Assert.assertNotNull(updatedList);
		Assert.assertEquals(numConf + 1, updatedList.size());

		conferenceService.delete(conference);

		log.info("END testFindConferenceByLocation().");
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.conference.service.ConferenceService#findConferencesStartedAfterDate(java.util.Date)}
	 * .
	 */
	@Test
	public void testFindConferencesStartedAfterDate() {
		log.info("test find conferences by start date ...");

		List<Conference> existList = conferenceService
				.findConferencesStartedAfterDate(startDate);
		Assert.assertNotNull(existList);
		int numExistList = existList.size();

		List<Conference> existAfterEndDateList = conferenceService
				.findConferencesStartedAfterDate(endDate);
		Assert.assertNotNull(existAfterEndDateList);
		int numExistAfterEndDate = existAfterEndDateList.size();

		log.info("create new conference for find test ...");
		Conference conference = new Conference("Test Conference",
				"Description for Test Conference", startDate, endDate,
				location1);
		conferenceService.create(conference);
		Assert.assertNotNull(conference.getId());

		List<Conference> newList = conferenceService
				.findConferencesStartedAfterDate(startDate);
		Assert.assertNotNull(newList);
		Assert.assertEquals(numExistList + 1, newList.size());

		List<Conference> newListAfterEndDate = conferenceService
				.findConferencesStartedAfterDate(endDate);
		Assert.assertNotNull(newListAfterEndDate);
		Assert.assertEquals(numExistAfterEndDate, newListAfterEndDate.size());

		conferenceService.delete(conference);

		log.info("END testFindConferencesStartedAfterDate().");
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.conference.service.ConferenceService#findConferencesByStartDateLocation(com.prodyna.pac.conference.location.model.Location, java.util.Date)}
	 * .
	 */
	@Test
	public void testFindConferencesByStartDateLocation() {
		log.info("test find conferences by start date and location ...");

		List<Conference> existList = conferenceService
				.findConferencesByStartDateLocation(location1, startDate);
		Assert.assertNotNull(existList);
		int numExistList = existList.size();

		List<Conference> existListLocation2 = conferenceService
				.findConferencesByStartDateLocation(location2, startDate);
		Assert.assertNotNull(existListLocation2);
		int numExistLocation2List = existListLocation2.size();

		List<Conference> existAfterEndDateList = conferenceService
				.findConferencesByStartDateLocation(location1, endDate);
		Assert.assertNotNull(existAfterEndDateList);
		int numExistAfterEndDate = existAfterEndDateList.size();

		log.info("create new conference for find test ...");
		Conference conference = new Conference("Test Conference",
				"Description for Test Conference", startDate, endDate,
				location1);
		conferenceService.create(conference);
		Assert.assertNotNull(conference.getId());

		List<Conference> newList = conferenceService
				.findConferencesByStartDateLocation(location1, startDate);
		Assert.assertNotNull(newList);
		Assert.assertEquals(numExistList + 1, newList.size());

		List<Conference> newListAfterEndDate = conferenceService
				.findConferencesByStartDateLocation(location1, endDate);
		Assert.assertNotNull(newListAfterEndDate);
		Assert.assertEquals(numExistAfterEndDate, newListAfterEndDate.size());

		List<Conference> newExistListLocation2 = conferenceService
				.findConferencesByStartDateLocation(location2, startDate);
		Assert.assertNotNull(newExistListLocation2);
		Assert.assertEquals(numExistLocation2List, newExistListLocation2.size());

		conferenceService.delete(conference);

	}

}
