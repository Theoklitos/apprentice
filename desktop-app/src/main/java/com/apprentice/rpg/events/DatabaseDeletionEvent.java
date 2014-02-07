package com.apprentice.rpg.events;

/**
 * Fired when an object is deleted
 * 
 * @author theoklitos
 * 
 */
public class DatabaseDeletionEvent<T> extends DatabaseModificationEvent<T> {

	public DatabaseDeletionEvent(final T deletedObject) {
		super(deletedObject);
	}

}
