package com.apprentice.rpg.dao;

import java.io.File;
import java.io.FileNotFoundException;

import com.apprentice.rpg.config.ApplicationConfiguration;
import com.apprentice.rpg.gui.GlobalWindowState;
import com.db4o.ext.Db4oException;

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
	 * Returns the single stored {@link ApplicationConfiguration} or creates a fresh one, if none yet exists
	 */
	ApplicationConfiguration getConfiguration();

	/**
	 * Returns the single stored {@link GlobalWindowState} or creates a fresh one, if none yet exists
	 */
	GlobalWindowState getGlobalWindowState();

	/**
	 * Loads the database from the given file.
	 * 
	 * @throws Db4oException
	 *             if there was an error during the loading of the db
	 * @throws FileNotFoundException
	 *             if the db file does not exist
	 */
	void loadDatabaseFromFile(File databaseFile) throws Db4oException, FileNotFoundException;

	/**
	 * Overwrites (or creates, if it does not exist) the given object
	 */
	void save(final Object object);

}
