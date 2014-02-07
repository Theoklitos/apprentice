package com.apprentice.rpg.dao.vaults;

import java.util.Collection;

import com.apprentice.rpg.dao.DataAccessObjectForAll;
import com.apprentice.rpg.dao.ItemAlreadyExistsEx;
import com.apprentice.rpg.dao.NoResultsFoundEx;
import com.apprentice.rpg.dao.TooManyResultsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.model.body.IType;
import com.google.inject.Inject;

/**
 * Contains all the types of beings in this rog system, for example, "Humanoid", "Winged Humanoid",
 * "Quadropod", etc
 * 
 * @author theoklitos
 * 
 * @deprecated use the singular {@link Vault} which is used to work with all types
 */
@Deprecated
public final class TypeVault implements ITypeVault {

	private final DatabaseConnection connection;
	private final Vault utils;

	@Inject
	public TypeVault(final Vault utils, final DatabaseConnection connection) {
		this.utils = utils;
		this.connection = connection;
	}
	
	@Override
	public void create(final IType object) {
		DataAccessObjectForAll.startTimer();
		if(utils.exists(object)) {
			throw new ItemAlreadyExistsEx("Object " + object + " was to be uniquely created but already exists!");
		}		
		connection.saveAndCommit(object);		
		DataAccessObjectForAll.stopTimerAndLog("Created \"IType\" object");		
	}

	@Override
	public final Collection<IType> getAll() {
		DataAccessObjectForAll.startTimer();
		final Collection<IType> result = connection.load(IType.class);
		DataAccessObjectForAll.stopTimerAndLog("Loaded all objects of type \"IType\"");
		return result;
	}
	
	@Override
	public IType getUniqueForName(final String name) throws TooManyResultsEx, NoResultsFoundEx {
		return utils.getUniqueNamedResult(name, IType.class);
	}

	@Override
	public void update(final IType item) {
		DataAccessObjectForAll.startTimer();
		connection.saveAndCommit(item);		
		DataAccessObjectForAll.stopTimerAndLog("Updated \"IType\" object");
	}
}
