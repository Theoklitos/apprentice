package com.apprentice.rpg.gui;

import com.apprentice.rpg.gui.main.MainFrame;
import com.apprentice.rpg.model.IPlayerCharacter;

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
	 * will guice, instatiate (and maybe restore the bounds) of the given frame.
	 */
	public void showFrame(final Class<? extends ApprenticeInternalFrame<?>> frameClass);

	/**
	 * will guice, instatiate (and maybe restore the bounds) of the given frame. Variable is used in
	 * parameterized frames such as {@link IPlayerCharacter} frames, edit weapon frames, etc
	 */
	public void showFrame(final Class<? extends ApprenticeInternalFrame<?>> frameClass, final String variable);

}
