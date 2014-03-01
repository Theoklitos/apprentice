package com.apprentice.rpg.gui;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.google.common.eventbus.EventBus;

/**
 * This control has a {@link Vault} and an {@link EventBus} and can perform various backend funtions
 * 
 * @author theoklitos
 * 
 */
public interface ControlWithVault {

	/**
	 * Stores the given {@link Nameable}, logs a message and fires the appropriate event
	 * 
	 * @throws NameAlreadyExistsEx
	 */
	void createOrUpdate(Nameable nameable, ItemType type) throws NameAlreadyExistsEx;

	/**
	 * Returns the global event bus
	 */
	ApprenticeEventBus getEventBus();

	/**
	 * Returns a reference to a/the repository
	 */
	Vault getVault();
}
