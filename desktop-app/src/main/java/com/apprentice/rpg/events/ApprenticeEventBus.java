package com.apprentice.rpg.events;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;

public interface ApprenticeEventBus {

	/**
	 * should be fired when a {@link BodyPart} is deleted
	 */
	void objectDeleteEvent(BodyPart bodyPart);

	/**
	 * should be fired when a {@link IType} is deleted
	 */
	void objectDeleteEvent(IType type);

	/**
	 * should be fired when a new {@link BodyPart} is created or modified
	 */
	void objectUpdateEvent(BodyPart newPart);

	/**
	 * should be fired when a new {@link Type} is created or modified
	 */
	void objectUpdateEvent(IType type);

	/**
	 * fires the given event
	 */
	void postEvent(DatabaseModificationEvent event);

	/**
	 * Registes a class which will listen to events via the @Subscribe annotation
	 */
	void register(Object eventHandler);

}
