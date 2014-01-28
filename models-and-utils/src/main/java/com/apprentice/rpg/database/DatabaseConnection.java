package com.apprentice.rpg.database;

import java.util.List;

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
	 * deletes the given object from the database. 
	 */
	void delete(Object item);

	/**
	 * Returns a string that describes where this database is located, i.e. if its a file, returns its path
	 * url
	 */
	String getLocation();

	/**
	 * returns a list of all the queried objects that exist in the database
	 */
	<T> List<T> load(Class<T> classToLoad);

	/**
	 * Opens the database
	 */
	void openDB() throws ApprenticeDatabaseEx;

	/**
	 * Overwrites (or creates, if it does not exist) the given object
	 * 
	 * @throws ApprenticeDatabaseEx
	 */
	void save(final Object item) throws ApprenticeDatabaseEx;

}
