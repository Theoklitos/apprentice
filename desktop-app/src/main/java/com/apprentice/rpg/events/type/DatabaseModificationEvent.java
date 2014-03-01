package com.apprentice.rpg.events.type;

import com.apprentice.rpg.model.Nameable;

/**
 * fired when the database is changed
 * 
 * @author theoklitos
 * 
 */
public class DatabaseModificationEvent<T> {

	private final T object;

	/**
	 * pass the object that triggered the update in the DB
	 */
	public DatabaseModificationEvent(final T object) {
		this.object = object;
	}

	/**
	 * returns the (new or updated) object that triggered this event
	 */
	public T getPayload() {
		return object;
	}

	@Override
	public String toString() {
		String payloadDescription = getPayload().getClass().getSimpleName();
		if (Nameable.class.isAssignableFrom(getPayload().getClass())) {
			payloadDescription = ((Nameable) getPayload()).getName();
		}
		return getClass().getSimpleName() + " for " + payloadDescription;
	}
}
