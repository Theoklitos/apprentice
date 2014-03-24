package com.apprentice.rpg.events;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.events.PublishSubscribeEventBus.EventType;
import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * tests for the {@link EventType}
 * 
 * @author theoklitos
 * 
 */
public class TestApprenticeEvent {

	private static final EventType EVENT_TYPE = EventType.CREATE;
	private ApprenticeEvent event;

	@Test
	public void isSameEventType() {
		assertTrue(event.isType(EventType.CREATE));
		assertFalse(event.isType(EventType.UPDATE));
		assertFalse(event.isType(EventType.DELETE));
	}

	@Test
	public void isSameTypes() {
		assertTrue(event.isType(EventType.CREATE, ItemType.WEAPON));
		assertFalse(event.isType(EventType.DELETE, ItemType.WEAPON));
		assertFalse(event.isType(EventType.CREATE, ItemType.AMMUNITION));
	}

	@Before
	public void setup() {
		event = new ApprenticeEvent(EVENT_TYPE, new DataFactory().getWeaponPrototypes().get(1));
	}
}
