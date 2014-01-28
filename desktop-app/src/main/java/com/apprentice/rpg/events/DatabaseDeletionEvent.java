package com.apprentice.rpg.events;

/**
 * Fired when an object is deleted
 * 
 * @author theoklitos
 * 
 */
public class DatabaseDeletionEvent extends DatabaseModificationEvent {

	public DatabaseDeletionEvent(final Object object) {
		super(object);
	}

}
