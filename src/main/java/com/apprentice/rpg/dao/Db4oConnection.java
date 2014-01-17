package com.apprentice.rpg.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

import com.apprentice.rpg.ApprenticeEx;
import com.apprentice.rpg.config.ApplicationConfiguration;
import com.apprentice.rpg.gui.GlobalWindowState;
import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oException;

/**
 * Implementation for db4o persistence
 * 
 * @author theoklitos
 * 
 */
public class Db4oConnection implements DatabaseConnection {

	private static Logger LOG = Logger.getLogger(Db4oConnection.class);

	private EmbeddedObjectContainer db;

	@Override
	public void closeDB() {
		LOG.debug("Database is commiting and shutting down.");
		db.commit();
		db.close();
	}

	@Override
	public ApplicationConfiguration getConfiguration() {
		return (ApplicationConfiguration) getUniqueObjectFromDB(ApplicationConfiguration.class);
	}

	@Override
	public GlobalWindowState getGlobalWindowState() {
		return (GlobalWindowState) getUniqueObjectFromDB(GlobalWindowState.class);
	}

	/**
	 * used for when needing to get unique objects from DB
	 */
	private Object getUniqueObjectFromDB(final Class<?> type) {
		final ObjectSet<?> resultSet = db.query(type);
		Object result = null;
		if (resultSet.size() != 1) {
			if (resultSet.size() > 1) {
				LOG.error("There were multiple " + type.getSimpleName()
					+ " objects stored. Check your DB code! Will create a fresh one.");
			}
			try {
				result = type.getConstructors()[0].newInstance();
			} catch (final InstantiationException e) {
				throw new ApprenticeEx(e);
			} catch (final IllegalAccessException e) {
				throw new ApprenticeEx(e);
			} catch (final IllegalArgumentException e) {
				throw new ApprenticeEx(e);
			} catch (final InvocationTargetException e) {
				throw new ApprenticeEx(e);
			} catch (final SecurityException e) {
				throw new ApprenticeEx(e);
			}
			save(result);
		} else {
			result = resultSet.get(0);
		}
		return result;
	}

	@Override
	public void loadDatabaseFromFile(final File databaseFile) throws Db4oException, FileNotFoundException {
		if (databaseFile.exists()) {
			final String locationString = databaseFile.getAbsolutePath();
			try {
				this.db = Db4oEmbedded.openFile(locationString);
			} catch (final Db4oException e) {
				throw new CouldNotLoadDatabaseEx(e);
			}
			LOG.info("Using database " + locationString + ".");
		} else {
			throw new FileNotFoundException();
		}
	}

	@Override
	public void save(final Object object) {
		db.store(object);
		LOG.debug("Stored " + object.getClass().getSimpleName() + " in the vault.");
	}

	@Override
	public String toString() {
		final StringBuffer result = new StringBuffer();
		for (final Object object : db.queryByExample(null)) {
			result.append("\n" + object.toString());
		}
		return result.toString();
	}
}
