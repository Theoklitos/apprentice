package com.apprentice.rpg.database;

import java.util.List;

import org.apache.log4j.Logger;

import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oIOException;

/**
 * Implementation for db4o persistence
 * 
 * @author theoklitos
 * 
 */
public class Db4oConnection implements DatabaseConnection {

	private static Logger LOG = Logger.getLogger(Db4oConnection.class);

	public EmbeddedObjectContainer database;
	private final String databaseLocation;

	public Db4oConnection(final EmbeddedObjectContainer database, final String databaseLocation) {
		this.database = database;
		database.ext().configure().updateDepth(Integer.MAX_VALUE);
		database.ext().configure().exceptionsOnNotStorable(false);
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
	public void delete(final Object item) {		
		database.delete(item);		
	}

	@Override
	public String getLocation() {
		return databaseLocation;
	}

	@Override
	public <T> List<T> load(final Class<T> classToLoad) {
		final ObjectSet<T> resultObjectSet = database.query(classToLoad);		
		return resultObjectSet.subList(0, resultObjectSet.size());
	}

	@Override
	public final void openDB() {
		if (database.ext().isClosed()) {
			try {
				database = Db4oEmbedded.openFile(databaseLocation);
			} catch (final Db4oIOException e) {
				throw new ApprenticeDatabaseEx(e);
			}
		} else {
			LOG.warn("Call to open database, but database was already open.");
		}
	}

	@Override
	public void save(final Object object) {
		activate(object);
		database.store(object);
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
