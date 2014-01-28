package com.apprentice.rpg.events;

/**
 * fired when the database is changed
 * 
 * @author theoklitos
 * 
 */
public class DatabaseModificationEvent {
	
	private final Object object;

	/**
	 * pass the object that triggered the update in the DB
	 */
	public DatabaseModificationEvent(final Object object) {
		this.object = object;		
	}
	
	/**
	 * returns the (new or updated) object that triggered this event
	 */
	public Object getPayload() {
		return object;
	}
}
