package com.apprentice.rpg.events;

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
}
