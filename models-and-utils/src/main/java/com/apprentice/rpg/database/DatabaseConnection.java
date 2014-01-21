package com.apprentice.rpg.dao;

import com.apprentice.rpg.config.ApplicationConfiguration;
import com.apprentice.rpg.gui.IGlobalWindowState;

/**
 * Connection to some persistent storage
 * 
 * @author theoklitos
 * 
 */
public interface DatabaseConnection {

	/**
	 * Gracefully commits & shuts down the database
	 */
	void closeDB();

	/**
	 * Commits the changes that are in memory into persistence (varies with each db implementation)
	 */
	void commit();

	/**
	 * Returns the single stored {@link ApplicationConfiguration} or creates a fresh one, if none yet exists
	 */
	ApplicationConfiguration getConfiguration();

	/**
	 * Returns the single stored {@link IGlobalWindowState} or creates a fresh one, if none yet exists
	 */
	IGlobalWindowState getGlobalWindowState();

	/**
	 * Returns a string that describes where this database is located, i.e. if its a file, returns its path
	 * url
	 */
	String getLocation();

	/**
	 * Overwrites (or creates, if it does not exist) the given object
	 */
	void save(final Object object);

}
