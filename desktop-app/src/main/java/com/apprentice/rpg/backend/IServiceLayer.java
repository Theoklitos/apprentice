package com.apprentice.rpg.backend;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * Encapsulates access to the backend. Has {@link Vault} and {@link ApprenticeEventBus} references and related
 * methods.
 * 
 * @author theoklitos
 * 
 */
public interface IServiceLayer {

	/**
	 * Stores the given {@link Nameable}, logs a message and fires the appropriate event
	 * 
	 * throws NameAlreadyExistsEx If the item is a prototype and there exists another prototype with the same
	 * name
	 */
	void createOrUpdate(Nameable item) throws NameAlreadyExistsEx;

	/**
	 * Stores the given {@link Nameable}, logs a message and fires the appropriate event. The name in this
	 * {@link Nameable} must be unique, other a {@link NameAlreadyExistsEx} will be thrown
	 * 
	 * @throws NameAlreadyExistsEx
	 *             if there exists an item
	 */
	void createOrUpdateUniqueName(Nameable item) throws NameAlreadyExistsEx;

	/**
	 * delets the {@link Nameable} with the given name. Returns true if the deletion was succesful and
	 * something was indeed deleted (and an event fired), false otherwise.
	 * 
	 */
	boolean deleteNameable(String name, ItemType itemType);

	/**
	 * Returns the global event bus
	 */
	ApprenticeEventBus getEventBus();

	/**
	 * returns a reference to the {@link IGlobalWindowState}
	 */
	public IGlobalWindowState getGlobalWindowState();

	/**
	 * Returns the time in a pretty String readable format of the given item's last update. If the item does
	 * not exist, will return a default string.
	 */
	String getLastUpdateTime(String typeName, ItemType type);

	/**
	 * Returns a reference to the repository
	 */
	Vault getVault();

	/**
	 * Tries to rename the nameable from newName -> oldName
	 * 
	 * @throws NameAlreadyExistsEx
	 *             if the newName already exists
	 */
	void renameNamebale(String oldName, String newName, ItemType itemType) throws NameAlreadyExistsEx;

}
