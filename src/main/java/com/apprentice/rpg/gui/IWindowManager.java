package com.apprentice.rpg.gui;

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
	 * Shows the log4j displayer frame
	 */
	void openLogFrame();

	/**
	 * Frame to create a new {@link PlayerCharacter}
	 */
	void openNewCharacterFrame();

}
