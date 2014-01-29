package com.apprentice.rpg.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.log4j.Logger;

import com.apprentice.rpg.database.ApprenticeDatabaseEx;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.util.Box;
import com.db4o.ext.Db4oException;
import com.google.inject.Inject;

/**
 * Is used as a front to access all (yet) types of classes.
 * 
 * @author theoklitos
 * 
 */
public class DataAccessObjectForAll implements Vault {

	private static Logger LOG = Logger.getLogger(DataAccessObjectForAll.class);

	private final DatabaseConnection connection;
	private static long startTimeMillis;

	/**
	 * Starts a timer, call stopTimerAndLog() to log a message with the duration
	 */
	public static void startTimer() {		
		startTimeMillis = System.currentTimeMillis();		 
	}

	/**
	 * You must have called startTimer first. This method will log the given message in level DEBUG along with
	 * the time the timer counted
	 */
	public static void stopTimerAndLog(final String taskName) {
		final long stopTimeMillis = System.currentTimeMillis();
		final long durationMillis = stopTimeMillis - startTimeMillis;
		String durationString;
		if (durationMillis < 1000) {
			durationString = durationMillis + "ms.";
		} else {
			final int seconds = (int) (durationMillis / 1000);
			final long carryMillis = durationMillis % 1000;
			durationString = seconds + " and " + carryMillis + " ms.";

		}
		startTimeMillis = 0;
		LOG.debug(taskName + ", duration: " + durationString);
	}

	@Inject
	public DataAccessObjectForAll(final DatabaseConnection connection) {
		this.connection = connection;
	}

	@Override
	public void checkDoubles(final List<? extends Nameable> nameableObjects) throws TooManyResultsEx {
		for (final Nameable object : nameableObjects) {
			int hitCount = 0;
			if (safelyCastObject(object).isEmpty()) {
				continue;
			}
			final Nameable nameableObject = safelyCastObject(object).getContent();
			for (final Object object2 : nameableObjects) {
				if (safelyCastObject(object2).isEmpty()) {
					continue;
				}
				final Nameable nameableObject2 = safelyCastObject(object2).getContent();
				if (nameableObject.getName().equals(nameableObject2.getName())) {
					hitCount++;
					if (hitCount > 1) {
						throw new TooManyResultsEx("Double with name \"" + nameableObject.getName()
							+ "\" found for type \"" + nameableObject.getClass().getSimpleName() + "\"");
					}
				}
			}
		}
	}

	@Override
	public void create(final Object item) throws ItemAlreadyExistsEx {
		if (exists(item, false)) {
			throw new ItemAlreadyExistsEx("Object " + item.getClass().getSimpleName()
				+ " was to be uniquely created but already exists!");
		} else {
			update(item);
		}
	}

	@Override
	public boolean delete(final Object item) {
		startTimer();
		if (exists(item,false)) {
			connection.delete(item);
			stopTimerAndLog("Deleted object \"" + item.getClass().getSimpleName() + "\"");
			return true;
		}
		return false;
	}
	
	@Override
	public boolean doesNameExist(final String name, final Class<? extends Nameable> memberOf) {
		try {
			getUniqueNamedResult(name, memberOf, false);
			return true;
		} catch (final NoResultsFoundEx e) {
			return false;
		} catch (final TooManyResultsEx e) {
			return true;
		}
	}

	@Override
	public <Τ> boolean exists(final Τ item) {
		return exists(item, true);
	}

	/**
	 * like exists(), but can turn logging on/off
	 */
	private <Τ> boolean exists(final Τ item, final boolean shouldLog) {
		final String message = "Determined object's \"" + item.getClass().getSimpleName() + "\" existence";
		if (shouldLog) {
			startTimer();
		}
		final List<?> result = getAll(item.getClass(), false);
		for (final Object resultObject : result) {
			if (resultObject.equals(item)) {
				if (shouldLog) {
					stopTimerAndLog(message);
				}
				return true;
			}
		}
		if (shouldLog) {
			stopTimerAndLog(message);
		}
		return false;
	}

	@Override
	public <T> List<T> getAll(final Class<T> type) {
		return getAll(type, true);
	}

	/**
	 * like getAll(), but can turn logging on/off
	 */
	public <T> List<T> getAll(final Class<T> type, final boolean shouldLog) {
		if (shouldLog) {
			startTimer();
		}
		try {
			final List<T> result = connection.load(type);
			if (shouldLog) {
				stopTimerAndLog("Loaded all objects of type \"" + type.getSimpleName() + "\"");
			}
			return result;
		} catch (final Db4oException e) {
			throw new ApprenticeDatabaseEx(e);
		}
	}

	@Override
	public <T extends Nameable> T getUniqueNamedResult(final String name, final Class<T> typeToQueryFor)
			throws TooManyResultsEx, NoResultsFoundEx {
		return getUniqueNamedResult(name, typeToQueryFor, true);
	}

	@SuppressWarnings("unchecked")
	public <T extends Nameable> T getUniqueNamedResult(final String name, final Class<T> typeToQueryFor,
			final boolean shouldLog) throws TooManyResultsEx, NoResultsFoundEx {
		if (shouldLog) {
			startTimer();
		}
		final List<T> result = connection.load(typeToQueryFor);
		checkDoubles(result);
		try {
			for (final Object resultObject : result) {
				if (resultObject instanceof Nameable) {
					if (((Nameable) resultObject).getName().equals(name)) {
						if (shouldLog) {
							stopTimerAndLog("Loaded one object of type \"" + typeToQueryFor.getSimpleName() + "\"");
						}
						return (T) resultObject;
					}
				}
			}
		} catch (final ClassCastException e) {
			// this is practically impossible to occur, let the code continue
			throw new NoResultsFoundEx("Found result with correct name \"" + name
				+ "\" but was of the wrong class type: " + e.getMessage());
		}
		throw new NoResultsFoundEx("No object of type \"" + typeToQueryFor.getSimpleName() + "\" with name \"" + name
			+ "\" exists.");
	}

	@Override
	@SuppressWarnings("unchecked")
	public final <T> T getUniqueObjectFromDB(final Class<T> type) {
		final List<T> resultSet = connection.load(type);
		T result = null;
		if (resultSet.size() != 1) {
			if (resultSet.size() > 1) {
				LOG.error("There were multiple " + type.getSimpleName()
					+ " objects stored. Check your database code! For now, will create a new one.");
				for (final Object reduntant : resultSet) {
					connection.delete(reduntant);
				}
			}
			try {
				result = (T) type.getConstructors()[0].newInstance();
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
			} catch (final ClassCastException e) {
				throw new ApprenticeEx(e);
			}
			connection.save(result);
		} else {
			result = resultSet.get(0);
		}
		return result;
	}

	/**
	 * Returns empty box and swallows the exception if not {@link Nameable}
	 */
	private Box<Nameable> safelyCastObject(final Object object) {
		try {
			return Box.with((Nameable) object);
		} catch (final ClassCastException e) {
			return Box.empty();
		}
	}

	@Override
	public void update(final Nameable item) {
		startTimer();
		for (final Nameable nameable : getAll(item.getClass(),false)) {			
			if (nameable.getName().equals(item.getName()) && !nameable.equals(item)) {
				throw new NameAlreadyExistsEx("There already exists one (or more) elements of type "
					+ item.getClass().getSimpleName() + " with name \"" + item.getName() + "\"");
			}
		}
		update((Object) item);
	}

	@Override
	public void update(final Object item) {
		String word = null;
		if (exists(item, false)) {
			word = "Updated ";
		} else {
			word = "Created ";
		}
		startTimer();
		connection.save(item);
		stopTimerAndLog(word + item.getClass().getSimpleName());
	}

}
