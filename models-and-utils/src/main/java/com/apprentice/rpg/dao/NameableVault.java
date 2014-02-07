package com.apprentice.rpg.dao;

import java.util.Collection;

import com.apprentice.rpg.model.Nameable;

/**
 * Contains {@link Nameable} objects with some retrieval methods
 * 
 * @author theoklitos
 * 
 */
public interface NameableVault {
	
	/**
	 * Returns true if there exists an object of the given class that is {@link Nameable} and has this name
	 */
	boolean doesNameExist(String name, Class<? extends Nameable> memberOf);

	/**
	 * returns all the {@link Nameable} of any type
	 */
	Collection<Nameable> getAll();

	/**
	 * Returns the single object that is stored of the given type for the given name. If more than one, or
	 * none, exist, exceptions will be thrown.
	 * 
	 * @throws TooManyResultsEx
	 * @throws NoResultsFoundEx
	 */
	<T extends Nameable> T getUniqueNamedResult(String name, Class<T> typeToQueryFor) throws TooManyResultsEx,
			NoResultsFoundEx;
	
	/**
	 * creates or updates(overwrite) the given Nameable object in the database, after checking for name
	 * duplication
	 * 
	 * @throws NameAlreadyExistsEx
	 */
	void update(Nameable item) throws NameAlreadyExistsEx;

}
