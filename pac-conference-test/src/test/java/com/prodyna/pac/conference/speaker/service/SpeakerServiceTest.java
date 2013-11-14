/**
 * 
 */
package com.prodyna.pac.conference.speaker.service;

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

import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.util.Resources;

/**
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@RunWith(Arquillian.class)
public class SpeakerServiceTest {

	@Inject
	private Logger log;

	@Inject
	private SpeakerService speakerService;

	private Speaker speaker;

	@Deployment
	public static Archive<?> createDeployment() {

		WebArchive war = ShrinkWrap.create(WebArchive.class,
				"pac-conference-speaker.war");
		war.addClasses(Speaker.class, SpeakerService.class,
				SpeakerServiceImpl.class, Resources.class);
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
		speaker = new Speaker("Mickey Mouse",
				"Mickey Mouse is a professional in entertaiment.");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		if (speaker.getId() != null) {
			speakerService.delete(speaker);
		}
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.speaker.service.SpeakerService#create(com.prodyna.pac.conference.speaker.model.Speaker)}
	 * .
	 */
	@Test
	public void testCreate() {
		log.info("test create speaker ...");
		if (speaker != null) {
			speakerService.create(speaker);
			Assert.assertNotNull(speaker.getId());
		}

		log.info("END testCreate().");
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.speaker.service.SpeakerService#get(java.lang.Long)}
	 * .
	 */
	@Test
	public void testGet() {
		log.info("test get speaker ...");
		log.info("create test speaker ...");
		speakerService.create(speaker);

		Assert.assertNotNull(speaker.getId());

		log.info("read created speaker ...");

		Speaker read = speakerService.get(speaker.getId());

		Assert.assertEquals(speaker, read);

	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.speaker.service.SpeakerService#getAll()}
	 * .
	 */
	@Test
	public void testGetAll() {
		log.info("test get all speakers ...");
		log.info("reate test speakers ...");
		
		List<Speaker> resultList = speakerService.getAll();
		Assert.assertNotNull(resultList);
		int extistNumSpeaker = resultList.size();
		
		speakerService.create(speaker);
		Assert.assertNotNull(speaker.getId());

		Speaker speaker2 = new Speaker("Donald Duck",
				"Donald Duck is a famous duck in entertaiment world.");
		speakerService.create(speaker2);
		Assert.assertNotNull(speaker2.getId());

		Speaker speaker3 = new Speaker("Mini Mouse",
				"Mini Mouse is a famous mouse in entertaiment world.");
		speakerService.create(speaker3);
		Assert.assertNotNull(speaker3.getId());

		resultList = speakerService.getAll();
		Assert.assertNotNull(resultList);
		Assert.assertEquals(extistNumSpeaker+3, resultList.size());

		log.info("remove created speaker ...");

		speakerService.delete(speaker3);
		speakerService.delete(speaker2);

		resultList = speakerService.getAll();
		Assert.assertNotNull(resultList);
		Assert.assertEquals(extistNumSpeaker+1, resultList.size());

		log.info("END testGetAll().");
	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.speaker.service.SpeakerService#update(com.prodyna.pac.conference.speaker.model.Speaker)}
	 * .
	 */
	@Test
	public void testUpdate() {
		log.info("test update speaker ...");

		String newName = "New speaker name";
		String newDescription = "New description";

		speakerService.create(speaker);
		log.info("update speaker with new values ...");
		speaker.setName(newName);
		speaker.setDescription(newDescription);

		speakerService.update(speaker);
		log.info("read speaker after update ...");
		Speaker aUp = speakerService.get(speaker.getId());

		Assert.assertNotNull(aUp.getId());
		Assert.assertEquals(newName, aUp.getName());
		Assert.assertEquals(newDescription, aUp.getDescription());

		log.info("END testUpdate().");

	}

	/**
	 * Test method for
	 * {@link com.prodyna.pac.conference.speaker.service.SpeakerService#delete(com.prodyna.pac.conference.speaker.model.Speaker)}
	 * .
	 */
	@Test
	public void testDelete() {
		log.info("test delete speaker ...");

		List<Speaker> speakerList = speakerService.getAll();
		int numSpeaker = speakerList.size();

		speakerService.create(speaker);
		Assert.assertNotNull(speaker.getId());
		Long speakerId = speaker.getId();

		speakerList = speakerService.getAll();
		Assert.assertEquals(numSpeaker + 1, speakerList.size());

		log.info("delete speaker ...");
		speakerService.delete(speaker);
		speaker.setId(null);

		Speaker read = speakerService.get(speakerId);
		Assert.assertNull(read);

		speakerList = speakerService.getAll();
		Assert.assertEquals(numSpeaker, speakerList.size());

		log.info("END testDelete().");

	}

}
