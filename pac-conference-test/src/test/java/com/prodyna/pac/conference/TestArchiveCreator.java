/**
 * 
 */
package com.prodyna.pac.conference;

import java.io.File;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.conference.service.ConferenceService;
import com.prodyna.pac.conference.conference.service.ConferenceServiceImpl;
import com.prodyna.pac.conference.conference.service.ConferenceServiceTest;
import com.prodyna.pac.conference.location.model.Location;
import com.prodyna.pac.conference.location.model.Room;
import com.prodyna.pac.conference.location.service.LocationService;
import com.prodyna.pac.conference.location.service.LocationServiceImpl;
import com.prodyna.pac.conference.location.service.LocationServiceTest;
import com.prodyna.pac.conference.location.service.RoomService;
import com.prodyna.pac.conference.location.service.RoomServiceImpl;
import com.prodyna.pac.conference.location.service.RoomServiceTest;
import com.prodyna.pac.conference.monitoring.logging.Logged;
import com.prodyna.pac.conference.monitoring.logging.LoggingInterceptor;
import com.prodyna.pac.conference.monitoring.notification.TalkChangedNotifierMDB;
import com.prodyna.pac.conference.monitoring.performance.Entry;
import com.prodyna.pac.conference.monitoring.performance.Monitored;
import com.prodyna.pac.conference.monitoring.performance.MonitoredInterceptor;
import com.prodyna.pac.conference.monitoring.performance.PerformanceMonitor;
import com.prodyna.pac.conference.monitoring.performance.PerformanceMonitorEnabler;
import com.prodyna.pac.conference.monitoring.performance.PerformanceMonitorMXBean;
import com.prodyna.pac.conference.rest.JaxRsActivator;
import com.prodyna.pac.conference.rest.RestUnknowExceptionHandler;
import com.prodyna.pac.conference.rest.admin.conference.ConferenceAdminRestService;
import com.prodyna.pac.conference.rest.admin.location.LocationAdminRestService;
import com.prodyna.pac.conference.rest.admin.location.LocationAdminRestServiceImpl;
import com.prodyna.pac.conference.rest.admin.location.RoomAdminRestService;
import com.prodyna.pac.conference.rest.admin.location.RoomAdminRestServiceImpl;
import com.prodyna.pac.conference.rest.admin.speaker.SpeakerAdminRestService;
import com.prodyna.pac.conference.rest.admin.talk.TalkAdminRestService;
import com.prodyna.pac.conference.rest.pub.conference.ConferencePublicRestService;
import com.prodyna.pac.conference.rest.pub.conference.ConferencePublicRestServiceImpl;
import com.prodyna.pac.conference.rest.pub.location.LocationPublicRestService;
import com.prodyna.pac.conference.rest.pub.location.LocationPublicRestServiceImpl;
import com.prodyna.pac.conference.rest.pub.location.RoomPublicRestService;
import com.prodyna.pac.conference.rest.pub.location.RoomPublicRestServiceImpl;
import com.prodyna.pac.conference.rest.pub.speaker.SpeakerPublicRestService;
import com.prodyna.pac.conference.rest.pub.talk.TalkPublicRestService;
import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.speaker.service.SpeakerService;
import com.prodyna.pac.conference.speaker.service.SpeakerServiceImpl;
import com.prodyna.pac.conference.speaker.service.SpeakerServiceTest;
import com.prodyna.pac.conference.talk.TalkUtil;
import com.prodyna.pac.conference.talk.model.Talk;
import com.prodyna.pac.conference.talk.model.TalkToRoom;
import com.prodyna.pac.conference.talk.model.TalkToRoomKey;
import com.prodyna.pac.conference.talk.model.TalkToSpeaker;
import com.prodyna.pac.conference.talk.model.TalkToSpeakerKey;
import com.prodyna.pac.conference.talk.service.TalkChangedDecorator;
import com.prodyna.pac.conference.talk.service.TalkService;
import com.prodyna.pac.conference.talk.service.TalkServiceImpl;
import com.prodyna.pac.conference.talk.service.TalkServiceTest;
import com.prodyna.pac.conference.talk.service.exception.OccupiedRoomException;
import com.prodyna.pac.conference.talk.service.exception.SpeakerNotAvailableException;
import com.prodyna.pac.conference.talk.service.exception.TalkOutOfConferenceDateBoundsException;
import com.prodyna.pac.conference.talk.service.exception.WrongLocationException;
import com.prodyna.pac.conference.util.Resources;

/**
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public class TestArchiveCreator {

	public static Archive<?> createDeployment() {
		EnterpriseArchive conferenceEar = ShrinkWrap.create(
				EnterpriseArchive.class, "pac-conference.ear");
		conferenceEar.addAsLibraries(createCDIJar(), createPersistenceJar(),
				createConferenceIntfJar(), createLocationIntfJar(),
				createSpeakerIntfJar(), createTalkIntfJar(),
				createMonitoringIntfJar());
		conferenceEar
				.addAsModules(createConferenceImplJar(),
						createLocationImplJar(), createSpeakerImplJar(),
						createTalkImplJar(), createMonitoringImplJar(),
						createRestWar());
		conferenceEar.setApplicationXML("application.xml");

		return conferenceEar;
	}

	public static Archive<?> createDeploymentForRest() {
		EnterpriseArchive conferenceEar = ShrinkWrap.create(
				EnterpriseArchive.class, "pac-conference.ear");
		conferenceEar.addAsLibraries(createCDIJar(), createPersistenceJar(),
				createConferenceIntfJar(), createLocationIntfJar(),
				createSpeakerIntfJar(), createTalkIntfJar(),
				createMonitoringIntfJar());
		conferenceEar.addAsModules(
				createConferenceImplJar().deleteClass(
						ConferenceServiceTest.class),
				createLocationImplJar().deleteClasses(
						LocationServiceTest.class, RoomServiceTest.class),
				createSpeakerImplJar().deleteClass(SpeakerServiceTest.class),
				createTalkImplJar().deleteClass(TalkServiceTest.class),
				createMonitoringImplJar(), createRestWar());
		conferenceEar.setApplicationXML("application.xml");

		return conferenceEar;
	}

	private static Archive<?> createConferenceIntfJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-conference-intf.jar");
		jar.addClasses(Conference.class, ConferenceService.class);

		return jar;
	}

	private static JavaArchive createConferenceImplJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-conference-impl.jar");
		jar.addClasses(ConferenceServiceImpl.class, ConferenceServiceTest.class);
		jar.addAsManifestResource(
				new File(
						"../pac-conference-ejb/pac-conference-conference/pac-conference-conference-impl/src/main/resources/META-INF/beans.xml"),
				"beans.xml");

		return jar;
	}

	private static Archive<?> createLocationIntfJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-location-intf.jar");
		jar.addClasses(Room.class, Location.class, RoomService.class,
				LocationService.class);

		return jar;
	}

	private static JavaArchive createLocationImplJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-location-impl.jar");
		jar.addClasses(RoomServiceImpl.class, LocationServiceImpl.class,
				RoomServiceTest.class, LocationServiceTest.class);
		jar.addAsManifestResource(
				new File(
						"../pac-conference-ejb/pac-conference-location/pac-conference-location-impl/src/main/resources/META-INF/beans.xml"),
				"beans.xml");

		return jar;
	}

	private static Archive<?> createSpeakerIntfJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-speaker-intf.jar");
		jar.addClasses(Speaker.class, SpeakerService.class);

		return jar;
	}

	private static JavaArchive createSpeakerImplJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-speaker-impl.jar");
		jar.addClasses(SpeakerServiceImpl.class, SpeakerServiceTest.class);
		jar.addAsManifestResource(
				new File(
						"../pac-conference-ejb/pac-conference-speaker/pac-conference-speaker-impl/src/main/resources/META-INF/beans.xml"),
				"beans.xml");

		return jar;
	}

	private static Archive<?> createTalkIntfJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-talk-intf.jar");
		jar.addClasses(Talk.class, TalkService.class,
				OccupiedRoomException.class,
				SpeakerNotAvailableException.class,
				WrongLocationException.class,
				TalkOutOfConferenceDateBoundsException.class);

		return jar;
	}

	private static JavaArchive createTalkImplJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-talk-impl.jar");
		jar.addClasses(TalkUtil.class, TalkToRoomKey.class, TalkToRoom.class,
				TalkToSpeakerKey.class, TalkToSpeaker.class,
				TalkServiceImpl.class, TalkServiceTest.class,
				TalkChangedDecorator.class);
		jar.addAsManifestResource(
				new File(
						"../pac-conference-ejb/pac-conference-talk/pac-conference-talk-impl/src/main/resources/META-INF/beans.xml"),
				"beans.xml");

		return jar;
	}

	private static Archive<?> createMonitoringIntfJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-monitoring-intf.jar");
		jar.addClasses(Monitored.class, PerformanceMonitorMXBean.class,
				Entry.class, Logged.class);

		return jar;
	}

	private static JavaArchive createMonitoringImplJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-monitoring-impl.jar");
		jar.addClasses(PerformanceMonitor.class,
				PerformanceMonitorEnabler.class, LoggingInterceptor.class,
				MonitoredInterceptor.class, TalkChangedNotifierMDB.class);
		jar.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		return jar;
	}

	private static Archive<?> createRestIntfJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-rest-intf.jar");
		jar.addClasses(ConferencePublicRestService.class,
				LocationPublicRestService.class, RoomPublicRestService.class,
				SpeakerPublicRestService.class, TalkPublicRestService.class,
				ConferenceAdminRestService.class,
				LocationAdminRestService.class, RoomAdminRestService.class,
				SpeakerAdminRestService.class, TalkAdminRestService.class);

		return jar;
	}

	private static JavaArchive createRestImplJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-rest-impl.jar");
		jar.addClasses(JaxRsActivator.class, RestUnknowExceptionHandler.class,
				ConferencePublicRestServiceImpl.class,
				LocationPublicRestServiceImpl.class,
				RoomPublicRestServiceImpl.class,
				LocationAdminRestServiceImpl.class,
				RoomAdminRestServiceImpl.class);
		jar.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		return jar;
	}

	private static Archive<?> createRestWar() {
		WebArchive war = ShrinkWrap.create(WebArchive.class,
				"pac-conference-rest.war");
		war.addAsLibraries(createRestIntfJar(), createRestImplJar());
		war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return war;
	}

	private static Archive<?> createCDIJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-cdi.jar");
		jar.addClasses(Resources.class);
		jar.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		return jar;
	}

	private static Archive<?> createPersistenceJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-persistence.jar");
		jar.addAsResource("META-INF/test-persistence.xml",
				"META-INF/persistence.xml");

		return jar;

	}
}
