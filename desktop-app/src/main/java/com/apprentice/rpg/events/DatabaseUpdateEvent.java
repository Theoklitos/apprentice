package com.apprentice.rpg.events;

/**
 * Fired on new or modified existing object in the DB
 * 
 * @author theoklitos
 * 
 */
public class DatabaseUpdateEvent<T> extends DatabaseModificationEvent<T> {

	public DatabaseUpdateEvent(final T updatedObject) {
		super(updatedObject);
	}
}
