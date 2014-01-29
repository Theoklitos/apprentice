package com.apprentice.rpg.dao;

import java.util.List;

import com.apprentice.rpg.model.Nameable;

/**
 * Used to access the database in a nice, controlled manner
 * 
 * @author theoklitos
 * 
 */
public interface Vault {

	/**
	 * if at least 2 {@link Nameable} objects share the same name, throws {@link TooManyResultsEx}
	 * 
	 * @throws TooManyResultsEx
	 */
	void checkDoubles(List<? extends Nameable> nameableObjects) throws TooManyResultsEx;

	/**
	 * Stores the object in the database, but will throw exception if such an object already exists
	 * 
	 * @throws ItemAlreadyExistsEx
	 */
	public void create(final Object item) throws ItemAlreadyExistsEx;

	/**
	 * deletes the given object, if it existed and deletion was sucesful, returns true.
	 */
	boolean delete(Object item);

	/**
	 * Returns true if there exists an object of the given class that is {@link Nameable} and has this name
	 */
	boolean doesNameExist(String name, Class<? extends Nameable> memberOf);

	/**
	 * returns true if this object is already stored in the database. Note: Its state can be different, all
	 * that matters is identity
	 */
	<T> boolean exists(T item);

	/**
	 * Returns all the items of type <T> stored in the database
	 */
	public <T> List<T> getAll(Class<T> type);

	/**
	 * Returns the single object that is persisted of the given type for the given name. If more than one, or
	 * none, exist, exceptions will be thrown.
	 * 
	 * @throws TooManyResultsEx
	 * @throws NoResultsFoundEx
	 */
	<T extends Nameable> T getUniqueNamedResult(String name, Class<T> typeToQueryFor) throws TooManyResultsEx,
			NoResultsFoundEx;

	/**
	 * Used for when needing to get unique objects from DB. If more than one of this type exist, will not
	 * throw exception but wiil log the error, delete all the objects and create a fresh new one. Warning:
	 * This uses reflection, so until the code is improved... <b>DO NOT CALL USING INTERFACES!</b>
	 */
	<T> T getUniqueObjectFromDB(Class<T> type);

	/**
	 * creates or updates(overwrite) the given Nameable object in the database, after checking for name
	 * duplication
	 * 
	 * @throws NameAlreadyExistsEx
	 */
	void update(Nameable item) throws NameAlreadyExistsEx;

	/**
	 * creates or updates(overwrite) the given object in the database
	 */
	public void update(final Object item);

}
