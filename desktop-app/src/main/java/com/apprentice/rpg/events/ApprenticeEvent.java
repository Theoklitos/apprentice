package com.apprentice.rpg.events;

import com.apprentice.rpg.events.PublishSubscribeEventBus.EventType;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.playerCharacter.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.util.Checker;

/**
 * Generated when an event occurs, holds information about it,
 * 
 * @author theoklitos
 * 
 */
public final class ApprenticeEvent {

	private final EventType eventType;
	private final Nameable object;

	public ApprenticeEvent(final EventType eventType, final Nameable cause) {
		Checker.checkNonNull("Events need at least an event type!", true, eventType);
		this.eventType = eventType;
		this.object = cause;
	}

	/**
	 * What object caused this event to occur?
	 */
	public Nameable getCause() {
		return object;
	}

	/**
	 * What type of event is this?
	 */
	public EventType getEventType() {
		return eventType;
	}

	/**
	 * returns true if the event is of the given event type
	 */
	public boolean isType(final EventType type) {
		return eventType.equals(type);
	}

	/**
	 * returns true if the event is of the given event type and for the given item type
	 */
	public boolean isType(final EventType eventType, final ItemType itemType) {
		try {
			return isType(eventType) && ItemType.getForClass(object.getClass()).equals(itemType);
		} catch (final ApprenticeEx e) {
			return false;
		}
	}

	@Override
	public String toString() {
		try {
			return eventType + " for " + ItemType.getForClass(object.getClass());
		} catch (final ApprenticeEx e) {
			return eventType.toString();
		}
	}

}
