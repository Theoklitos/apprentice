package com.apprentice.rpg.gui.windowState;

import java.awt.Rectangle;
import java.util.Collection;

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
	 * returns all the IDs for the persisted {@link WindowState}
	 */
	Collection<WindowStateIdentifier> getAllFrameIdentifiers();

	/**
	 * returns the IDs of any frames that are open
	 */
	Collection<WindowStateIdentifier> getOpenInternalFrames();

	/**
	 * Returns a box with the stored {@link WindowState}, if such a window is registered
	 */
	Box<WindowState> getWindowState(final WindowStateIdentifier identifier);

	/**
	 * returns a reference to the {@link IWindowUtils} instance that this object uses
	 */
	public IWindowUtils getWindowUtils();

	/**
	 * Sets the given window's state to open or not open
	 * 
	 * @throws FrameNotOpenEx if such a window does not exist
	 */
	void setWindowOpen(WindowStateIdentifier identifier, final boolean isOpen);

	/**
	 * updates (replaces) or creates the window's {@link WindowState} information.
	 */
	void setWindowState(final WindowStateIdentifier identifier, final Rectangle bounds, final boolean isOpen);

}
