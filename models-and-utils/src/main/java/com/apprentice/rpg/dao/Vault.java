package com.apprentice.rpg.dao;

import java.util.Collection;

import com.apprentice.rpg.dao.simple.NameableVault;
import com.apprentice.rpg.dao.time.ModificationTimeVault;
import com.apprentice.rpg.model.Nameable;

/**
 * Main interface to the database, a vault is a big Data Access Object
 * 
 * @author theoklitos
 * 
 */
public interface Vault extends NameableVault, ModificationTimeVault {

	/**
	 * if at least 2 {@link Nameable} objects share the same name, throws {@link TooManyResultsEx}
	 * 
	 * @throws TooManyResultsEx
	 */
	void checkDoubles(Collection<? extends Nameable> nameableObjects) throws TooManyResultsEx;

	/**
	 * Stores the object in the database, but will throw exception if such an object already exists
	 * 
	 * @throws ItemAlreadyExistsEx
	 */
	public void create(final Object item) throws ItemAlreadyExistsEx;

	/**
	 * will deelte all the objects inside the given {@link NameableVault} that exist in this vault
	 */
	void delete(final NameableVault vault);
	
	/**
	 * deletes the given object, if it existed and deletion was sucesful, returns true.
	 */
	boolean delete(Object item);

	/**
	 * returns true if this object is already stored. Note: Its state can be different, all that matters is
	 * identity
	 */
	<T> boolean exists(T item);

	/**
	 * Returns all the items of type <T> that are stored.
	 */
	public <T> Collection<T> getAll(Class<T> type);

	/**
	 * Used for when needing to get unique objects from DB. If more than one of this type exist, will not
	 * throw exception but wiil log the error, delete all the objects and create a fresh new one. Warning:
	 * This uses reflection, so until the code is improved... <b>DO NOT CALL USING INTERFACES!</b>
	 */
	<T> T getUniqueObjectFromDB(Class<T> type);

	/**
	 * will update all the objects inside the given {@link NameableVault}
	 * 
	 * @throws NameAlreadyExistsEx
	 *             if the update changed the name (in case of a {@link Nameable}) to an existing one
	 */
	public void update(final NameableVault vault) throws NameAlreadyExistsEx;

	/**
	 * creates or updates(overwritea) the given object
	 */
	public void update(final Object item) throws NameAlreadyExistsEx;

}
