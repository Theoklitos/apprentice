package com.apprentice.rpg.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.events.PublishSubscribeEventBus.EventType;
import com.apprentice.rpg.events.type.BodyPartCreationEvent;
import com.apprentice.rpg.events.type.BodyPartDeletionEvent;
import com.apprentice.rpg.events.type.BodyPartUpdateEvent;
import com.apprentice.rpg.events.type.DatabaseModificationEvent;
import com.apprentice.rpg.events.type.TypeCreationEvent;
import com.apprentice.rpg.events.type.TypeDeletionEvent;
import com.apprentice.rpg.events.type.TypeUpdateEvent;
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

	private DatabaseModificationEvent<?> eventBuffer;
	private PublishSubscribeEventBus eventBus;
	private DataFactory factory;

	@Test
	public void correctFiringArmorPieceEvents() {
		fail("implement me!");
	}

	@Test
	public void correctFiringBodyPartEvents() {
		final BodyPart bodyPart = new BodyPart("test");
		eventBus.postDeleteEvent(bodyPart);
		assertEquals(BodyPartDeletionEvent.class, eventBuffer.getClass());
		assertEquals(bodyPart, eventBuffer.getPayload());

		eventBus.postUpdateEvent(bodyPart);
		assertEquals(BodyPartUpdateEvent.class, eventBuffer.getClass());
		assertEquals(bodyPart, eventBuffer.getPayload());

		eventBus.postCreationEvent(bodyPart);
		assertEquals(BodyPartCreationEvent.class, eventBuffer.getClass());
		assertEquals(bodyPart, eventBuffer.getPayload());
	}

	@Test
	public void correctFiringStrikeEvents() {
		fail("implement me!");
	}

	@Test
	public void correctFiringTypeEvents() {
		final IType type = factory.getTypes().get(1);
		eventBus.postEvent(type, EventType.DELETE);
		assertEquals(TypeDeletionEvent.class, eventBuffer.getClass());
		assertEquals(type, eventBuffer.getPayload());

		eventBus.postEvent(type, EventType.UPDATE);
		assertEquals(TypeUpdateEvent.class, eventBuffer.getClass());
		assertEquals(type, eventBuffer.getPayload());

		eventBus.postEvent(type, EventType.CREATE);
		assertEquals(TypeCreationEvent.class, eventBuffer.getClass());
		assertEquals(type, eventBuffer.getPayload());
	}

	@Test
	public void correctFiringWeaponEvents() {
		fail("implement me!");
	}

	@Subscribe
	public void receive(final DatabaseModificationEvent<?> event) {
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
