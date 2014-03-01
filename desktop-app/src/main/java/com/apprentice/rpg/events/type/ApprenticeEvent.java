package com.apprentice.rpg.events.type;

/**
 * An event inside the apprentice app
 * 
 * @author theoklitos
 * 
 */
public interface ApprenticeEvent<T> {
	/**
	 * returns the updated/modified/deleted object that triggered this event
	 */
	public T getPayload();
}
