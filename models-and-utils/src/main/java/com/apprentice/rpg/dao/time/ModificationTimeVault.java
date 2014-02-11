package com.apprentice.rpg.dao.time;

import org.joda.time.DateTime;

import com.apprentice.rpg.model.Nameable;

public interface ModificationTimeVault {

	/**
	 * returned when there is no modification time for an object
	 */
	public final static String NO_TIMING_DESCRIPTION = "Not Stored";

	/**
	 * returns the time this {@link Nameable} was updated, in a nice readable way. Non-existing item will
	 * return "Not Stored"
	 */
	String getPrettyUpdateTime(Nameable item);

	/**
	 * Stores information regarding when a {@link Nameable} was stored/created/updated
	 */
	void updated(DateTime when, Nameable item);

}
