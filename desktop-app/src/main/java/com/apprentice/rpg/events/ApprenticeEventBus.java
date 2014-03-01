package com.apprentice.rpg.events;

import com.apprentice.rpg.events.PublishSubscribeEventBus.EventType;
import com.apprentice.rpg.events.type.DatabaseModificationEvent;
import com.apprentice.rpg.model.Nameable;

public interface ApprenticeEventBus {

	/**
	 * will fire an "create" event type for the given object
	 */
	void postCreationEvent(Nameable nameable);

	/**
	 * will fire a "delete" event type for the given object
	 */
	void postDeleteEvent(Nameable itemAtHand);

	/**
	 * fires the given event
	 */
	void postEvent(DatabaseModificationEvent<?> event);

	/**
	 * will fire the specificed {@link EventType} for the given object
	 */
	void postEvent(Nameable nameable, EventType eventType);

	/**
	 * will fire an "update" event type for the given object
	 */
	void postUpdateEvent(Nameable itemAtHand);

	/**
	 * Registes a class which will listen to events via the @Subscribe annotation
	 */
	void register(Object eventHandler);

}
