/**
 * 
 */
package com.prodyna.pac.conference;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

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
import com.prodyna.pac.conference.talk.service.TalkService;
import com.prodyna.pac.conference.talk.service.TalkServiceImpl;
import com.prodyna.pac.conference.talk.service.TalkServiceTest;
import com.prodyna.pac.conference.talk.service.exception.OccupiedRoomException;
import com.prodyna.pac.conference.talk.service.exception.SpeakerNotAvailableException;
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
				createSpeakerIntfJar(), createTalkIntfJar());
		conferenceEar.addAsModules(createConferenceImplJar(),
				createLocationImplJar(), createSpeakerImplJar(),
				createTalkImplJar());
		conferenceEar.setApplicationXML("application.xml");

		return conferenceEar;
	}

	private static Archive<?> createConferenceIntfJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-conference-intf.jar");
		jar.addClasses(Conference.class, ConferenceService.class);

		return jar;
	}

	private static Archive<?> createConferenceImplJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-conference-impl.jar");
		jar.addClasses(ConferenceServiceImpl.class, ConferenceServiceTest.class);
		jar.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		return jar;
	}

	private static Archive<?> createLocationIntfJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-location-intf.jar");
		jar.addClasses(Room.class, Location.class, RoomService.class,
				LocationService.class);

		return jar;
	}

	private static Archive<?> createLocationImplJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-location-impl.jar");
		jar.addClasses(RoomServiceImpl.class, LocationServiceImpl.class,
				RoomServiceTest.class, LocationServiceTest.class);
		jar.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		return jar;
	}

	private static Archive<?> createSpeakerIntfJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-speaker-intf.jar");
		jar.addClasses(Speaker.class, SpeakerService.class);

		return jar;
	}

	private static Archive<?> createSpeakerImplJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-speaker-impl.jar");
		jar.addClasses(SpeakerServiceImpl.class, SpeakerServiceTest.class);
		jar.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		return jar;
	}

	private static Archive<?> createTalkIntfJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-talk-intf.jar");
		jar.addClasses(Talk.class, TalkService.class,
				OccupiedRoomException.class,
				SpeakerNotAvailableException.class,
				WrongLocationException.class);

		return jar;
	}

	private static Archive<?> createTalkImplJar() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"pac-conference-talk-impl.jar");
		jar.addClasses(TalkUtil.class, TalkToRoomKey.class, TalkToRoom.class,
				TalkToSpeakerKey.class, TalkToSpeaker.class, TalkServiceImpl.class,
				TalkServiceTest.class);
		jar.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		return jar;
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
