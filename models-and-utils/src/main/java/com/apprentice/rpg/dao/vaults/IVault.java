package com.apprentice.rpg.dao.vaults;

import java.util.Collection;

import com.apprentice.rpg.dao.ItemAlreadyExistsEx;
import com.apprentice.rpg.dao.NoResultsFoundEx;
import com.apprentice.rpg.dao.TooManyResultsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.model.playerCharacter.Nameable;

/**
 * A vault is a repository, and each individual repository is resposnible for persisting a type/class of
 * objects (DAO)
 * 
 * @author theoklitos
 * 
 * @deprecated use the singular {@link Vault} which is used to work with all types 
 */
@Deprecated
public interface IVault<T> {

	/**
	 * Stores the object in the database, but will throw exception if such an object already exists
	 * 
	 * @throws ItemAlreadyExistsEx
	 */
	public void create(final T object) throws ItemAlreadyExistsEx;

	/**
	 * Returns all the items of type <T> stored in the database
	 */
	public Collection<T> getAll();

	/**
	 * Returns one and only one <T> object that must be {@link Nameable} and also has the given name.
	 * 
	 * @throws {@link TooManyResultsEx}
	 * @throws {@link NoResultsFoundEx
	 */
	public T getUniqueForName(final String name) throws TooManyResultsEx, NoResultsFoundEx;

	/**
	 * creates or updates(overwrite) the given object in the database
	 */
	public void update(final T item);

}
