package com.apprentice.rpg.gui;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.gui.description.DescriptionPanel;

/**
 * This control can control a {@link DescriptionPanel} tha exists in a view
 * 
 * @author theoklitos
 * 
 */
public interface ControlForDescriptionInView {

	/**
	 * Returns the global event bus
	 */
	ApprenticeEventBus getEventBus();

	/**
	 * Returns a reference to a/the repository
	 */
	Vault getVault();
}
