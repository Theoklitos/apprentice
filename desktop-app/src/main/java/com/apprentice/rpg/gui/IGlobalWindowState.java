package com.apprentice.rpg.gui;

import java.awt.Rectangle;

import com.apprentice.rpg.gui.util.IWindowUtils;
import com.apprentice.rpg.util.Box;

/**
 * Information about all the spawned windows and their positions/sizes
 * 
 * @author theoklitos
 * 
 */
public interface IGlobalWindowState {

	/**
	 * Returns a box with the stored {@link WindowState}, if such a window is registered
	 */
	Box<WindowState> getWindowState(final String name);

	/**
	 * returns a reference to the {@link IWindowUtils} instance that this object uses
	 */
	public IWindowUtils getWindowUtils();

	/**
	 * Sets the given window's state to "open". If such a window has not been registered, does nothing
	 */
	void setWindowOpen(String name);

	/**
	 * updates (replaces) the window's {@link WindowState} information.
	 */
	void updateWindow(final String name, final Rectangle bounds, final boolean isOpen);

}
