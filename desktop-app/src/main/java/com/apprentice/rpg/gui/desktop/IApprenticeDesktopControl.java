package com.apprentice.rpg.gui.desktop;

import java.awt.Color;
import java.awt.Rectangle;

import com.apprentice.rpg.gui.ControlForView;

public interface IApprenticeDesktopControl extends ControlForView<ApprenticeDesktop> {

	/**
	 * Gets the {@link Rectangle} bounds of the {@link ApprenticeDesktop}
	 */
	Rectangle getDesktopBounds();

	/**
	 * Sets the desktop background as a specific color
	 */
	void setBackgroundColor(Color color);

	/**
	 * Sets the background to the stored color/image
	 */
	void setBackgroundFromConfig();

	/**
	 * Sets the background as the image in the given filepath
	 */
	void setBackgroundImage(String imagePath);

	/**
	 * Sets the background to the default image
	 */
	void setDefaultDesktopBackground();

}
