/**
 * 
 */
package com.prodyna.pac.conference.rest;

import java.net.URL;
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
import com.prodyna.pac.conference.rest.admin.location.LocationAdminRestService;
import com.prodyna.pac.conference.rest.pub.conference.ConferencePublicRestService;
import com.prodyna.pac.conference.rest.pub.location.LocationPublicRestService;

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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
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
	public void testConferencePublicRestServiceGetAll() {
		String serviceURL = deploymentUrl.toString() + RESOURCE_PREFIX
				+ "/public/conference";
		System.out.println("Conference Rest Service URL: " + serviceURL);

		ConferencePublicRestService confRestService = ProxyFactory.create(
				ConferencePublicRestService.class, serviceURL);

		ClientResponse<?> response = (ClientResponse<?>) confRestService
				.getAll();

		Assert.assertEquals(Response.Status.OK.getStatusCode(),
				response.getStatus());
		System.out.println("Response: " + response + " status "
				+ response.getStatus());
		List conferenceList = response.getEntity(List.class);
		Assert.assertNotNull(conferenceList);
		System.out.println("first conference in the list: "
				+ conferenceList.get(0).toString());
	}
}
