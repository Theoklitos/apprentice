package com.apprentice.rpg.dao;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

import com.apprentice.rpg.ApprenticeEx;
import com.apprentice.rpg.config.ApplicationConfiguration;
import com.apprentice.rpg.gui.GlobalWindowState;
import com.apprentice.rpg.gui.IGlobalWindowState;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectSet;

/**
 * Implementation for db4o persistence
 * 
 * @author theoklitos
 * 
 */
public class Db4oConnection implements DatabaseConnection {

	private static Logger LOG = Logger.getLogger(Db4oConnection.class);

	// TODO
	public final EmbeddedObjectContainer database;
	private final String databaseLocation;

	public Db4oConnection(final EmbeddedObjectContainer database, final String databaseLocation) {
		this.database = database;
		this.databaseLocation = databaseLocation;
	}

	/**
	 * activates up to infinity
	 */
	private void activate(final Object object) {
		database.activate(object, Integer.MAX_VALUE);
	}

	@Override
	public void closeDB() {
		LOG.debug("Database is commiting and shutting down.");
		commit();
		database.close();
	}

	@Override
	public void commit() {
		database.commit();
	}

	@Override
	public ApplicationConfiguration getConfiguration() {
		return (ApplicationConfiguration) getUniqueObjectFromDB(ApplicationConfiguration.class);
	}

	@Override
	public IGlobalWindowState getGlobalWindowState() {
		return (IGlobalWindowState) getUniqueObjectFromDB(GlobalWindowState.class);
	}

	@Override
	public String getLocation() {
		return databaseLocation;
	}

	/**
	 * used for when needing to get unique objects from DB
	 */
	private Object getUniqueObjectFromDB(final Class<?> type) {
		final ObjectSet<?> resultSet = database.query(type);
		Object result = null;
		if (resultSet.size() != 1) {
			if (resultSet.size() > 1) {
				LOG.error("There were multiple " + type.getSimpleName()
					+ " objects stored. Check your database code! For now, will create a new one.");
				for (final Object reduntant : resultSet) {
					database.delete(reduntant);
				}
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
			activate(result);
		}
		return result;
	}

	@Override
	public void save(final Object object) {
		activate(object);
		database.store(object);
		LOG.debug("Stored " + object.getClass().getSimpleName() + " in the vault.");
	}

	@Override
	public String toString() {
		final StringBuffer result = new StringBuffer();
		for (final Object object : database.queryByExample(null)) {
			result.append("\n" + object.toString());
		}
		return result.toString();
	}
}
