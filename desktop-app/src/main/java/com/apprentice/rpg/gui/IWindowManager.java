package com.apprentice.rpg.gui;

import com.apprentice.rpg.gui.database.DatabaseSettingsFrame;
import com.apprentice.rpg.gui.main.MainFrame;
import com.apprentice.rpg.model.PlayerCharacter;

/**
 * Guiced up factory for frames and windows, maintains list of active windows
 * 
 * @author theoklitos
 * 
 */
public interface IWindowManager {

	/**
	 * Creates and shows the {@link MainFrame}
	 */
	void initializeMainFrame();

	/**
	 * Shows the {@link DatabaseSettingsFrame}
	 */
	void showDatabaseSettingsFrame();

	/**
	 * Shows the log4j displayer frame
	 */
	void showLogFrame();

	/**
	 * Shows the frame used to create a new {@link PlayerCharacter}
	 */
	void showNewCharacterFrame();

	/**
	 * Shows the {@link exportItems}
	 */
	void showTypeAndBodyPartFrame();

}
