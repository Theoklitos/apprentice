package com.apprentice.rpg.events;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.events.PublishSubscribeEventBus.EventType;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.factories.DataFactory;
import com.google.common.eventbus.Subscribe;

/**
 * Tests for the {@link PublishSubscribeEventBus}
 * 
 * @author theoklitos
 * 
 */
public final class TestPublishSubscribeEventBus {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(TestPublishSubscribeEventBus.class);

	private ApprenticeEvent eventBuffer;
	private PublishSubscribeEventBus eventBus;
	private DataFactory factory;

	@Test
	public void correctFiringBodyPartEvents() {
		final BodyPart bodyPart = new BodyPart("test");
		eventBus.postDeleteEvent(bodyPart);
		assertEquals(EventType.DELETE, eventBuffer.getEventType());
		assertEquals(bodyPart, eventBuffer.getCause());

		eventBus.postUpdateEvent(bodyPart);
		assertEquals(EventType.UPDATE, eventBuffer.getEventType());
		assertEquals(bodyPart, eventBuffer.getCause());

		eventBus.postCreationEvent(bodyPart);
		assertEquals(EventType.CREATE, eventBuffer.getEventType());
		assertEquals(bodyPart, eventBuffer.getCause());
	}

	@Test
	public void correctFiringTypeEvents() {
		final IType type = factory.getTypes().get(1);
		eventBus.postApprenticeEvent(type, EventType.DELETE);
		assertEquals(EventType.DELETE, eventBuffer.getEventType());
		assertEquals(type, eventBuffer.getCause());

		eventBus.postApprenticeEvent(type, EventType.UPDATE);
		assertEquals(EventType.UPDATE, eventBuffer.getEventType());
		assertEquals(type, eventBuffer.getCause());

		eventBus.postApprenticeEvent(type, EventType.CREATE);
		assertEquals(EventType.CREATE, eventBuffer.getEventType());
		assertEquals(type, eventBuffer.getCause());
	}

	@Subscribe
	public void receive(final ApprenticeEvent event) {
		eventBuffer = event;
	}

	@Before
	public void setup() {
		factory = new DataFactory();
		eventBus = new PublishSubscribeEventBus();
		eventBus.register(this);
	}

	@After
	public void teardown() {
		eventBuffer = null;
	}

}
