package com.apprentice.rpg.gui;

import com.apprentice.rpg.gui.database.DatabaseSettingsFrame;
import com.apprentice.rpg.gui.main.MainFrame;
import com.apprentice.rpg.gui.windowState.WindowStateIdentifier;
import com.apprentice.rpg.model.PlayerCharacter;

/**
 * Guiced up factory for frames and windows, maintains list of active windows
 * 
 * @author theoklitos
 * 
 */
public interface IWindowManager {

	/**
	 * closes all the internal frames
	 */
	void closeAllFrames();

	/**
	 * Creates and shows the {@link MainFrame}
	 */
	void initializeMainFrame();

	/**
	 * will display the corresponding internal frame of this identifier
	 */
	void openFrame(WindowStateIdentifier openFrameIdentifier);

	/**
	 * Shows the {@link DatabaseSettingsFrame}
	 */
	void showDatabaseSettingsFrame();

	/**
	 * Shows the dice roller frame
	 */
	void showDiceRollerFrame();

	/**
	 * Shows the log4j displayer frame
	 */
	void showLogFrame();

	/**
	 * Shows the frame used to create a new {@link PlayerCharacter}
	 */
	void showNewPlayerCharacterFrame();

	/**
	 * Shows the 
	 */
	void showPlayerVaultFrame();

	/**
	 * Shows the {@link TypeAndBodyPartFrameP}
	 */
	void showTypeAndBodyPartFrame();

}
