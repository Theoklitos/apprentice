package com.apprentice.rpg.dao.simple;

import java.util.Collection;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.NoResultsFoundEx;
import com.apprentice.rpg.dao.TooManyResultsEx;
import com.apprentice.rpg.model.Nameable;

/**
 * Contains {@link Nameable} objects with some retrieval methods
 * 
 * @author theoklitos
 * 
 */
public interface NameableVault {

	/**
	 * adds all the given elements to the vault
	 */
	public void addAll(Collection<? extends Nameable> nameables);

	/**
	 * Returns true if there exists an object of the given class that is {@link Nameable} and has this name
	 */
	boolean doesNameExist(String name, Class<? extends Nameable> memberOf);

	/**
	 * returns all the {@link Nameable} of any type
	 */
	Collection<Nameable> getAllNameables();

	/**
	 * returns all the {@link Nameable} of the given type
	 */
	<T extends Nameable> Collection<T> getAllNameables(Class<T> nameableType);

	/**
	 * returns all the prototype {@link Nameable} of the given type
	 */
	<T extends Nameable> Collection<T> getAllPrototypeNameables(Class<T> nameableType);

	/**
	 * Returns the single prototypical object that is stored of the given type for the given name. If more
	 * than one, or none, exist, exceptions will be thrown.
	 * 
	 * @throws TooManyResultsEx
	 * @throws NoResultsFoundEx
	 */
	<T extends Nameable> T getPrototype(final String name, final Class<T> typeToQueryFor) throws TooManyResultsEx,
			NoResultsFoundEx;

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
	 *             if the item is a prototype and there is a collision with another prototype of the same type
	 */
	void update(Nameable object) throws NameAlreadyExistsEx;

}
