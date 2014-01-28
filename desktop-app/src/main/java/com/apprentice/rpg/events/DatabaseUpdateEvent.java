package com.apprentice.rpg.events;

/**
 * Fired on new or modified existing object in the DB
 * 
 * @author theoklitos
 *
 */
public class DatabaseUpdateEvent extends DatabaseModificationEvent {

	public DatabaseUpdateEvent(final Object object) {
		super(object);
	}
}
