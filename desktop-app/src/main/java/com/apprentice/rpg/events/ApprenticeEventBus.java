package com.apprentice.rpg.events;

import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;

public interface ApprenticeEventBus {

	/**
	 * should be fired when a {@link BodyPart} is deleted
	 */
	void postDeleteEvent(BodyPart bodyPart);

	/**
	 * should be fired when a {@link IType} is deleted
	 */
	void postDeleteEvent(IType type);

	/**
	 * should be fired when a new {@link BodyPart} is created or modified
	 */
	void postUpdateEvent(BodyPart updatedBodyPart);

	/**
	 * should be fired when a new {@link Type} is created or modified
	 */
	void postUpdateEvent(IType updatedType);

	/**
	 * fires the given event
	 */
	void postEvent(DatabaseModificationEvent<?> event);

	/**
	 * posts the correct {@link DatabaseUpdateEvent} based on the concrete class of this object
	 */
	void postUpdateEvent(Nameable object);

	/**
	 * Registes a class which will listen to events via the @Subscribe annotation
	 */
	void register(Object eventHandler);

}
