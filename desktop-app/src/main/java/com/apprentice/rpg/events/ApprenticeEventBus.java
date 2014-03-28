package com.apprentice.rpg.events;

import com.apprentice.rpg.events.PublishSubscribeEventBus.EventType;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.model.playerCharacter.Nameable;

public interface ApprenticeEventBus {

	/**
	 * fires the given event
	 */
	void postApprenticeEvent(ApprenticeEvent event);

	/**
	 * will fire the specificed {@link EventType} for the given object
	 */
	void postApprenticeEvent(Nameable nameable, EventType eventType);

	/**
	 * will fire an "create" event type for the given object
	 */
	void postCreationEvent(Nameable nameable);

	/**
	 * will fire a "delete" event type for the given object
	 */
	void postDeleteEvent(Nameable itemAtHand);

	/**
	 * Will open the given internal frame.
	 */
	void postShowFrameEvent(final Class<? extends ApprenticeInternalFrame<?>> frameToShow);

	/**
	 * Will open the given internal frame with the given parameter
	 */
	void postShowFrameEvent(final Class<? extends ApprenticeInternalFrame<?>> frameToShow, final String parameter);

	/**
	 * Posts a {@link ShutdownEvent}, notifying all the frames to close
	 * 
	 * @param shouldReopen
	 *            set to true during shutdown, this will store the frames' state and cause them to reopen at
	 *            startup
	 */
	void postShutdownEvent(boolean shouldReopen);

	/**
	 * will fire an "update" event type for the given object
	 */
	void postUpdateEvent(Nameable itemAtHand);
	
	/**
	 * Registes a class which will listen to events via the @Subscribe annotation
	 */
	void register(Object eventHandler);

}
