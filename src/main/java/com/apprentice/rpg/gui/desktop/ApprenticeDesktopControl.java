package com.apprentice.rpg.gui.desktop;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

import org.apache.log4j.Logger;

import com.apprentice.rpg.ApprenticeEx;
import com.apprentice.rpg.config.ApplicationConfiguration;
import com.apprentice.rpg.config.ApplicationConfiguration.DesktopBackgroundType;
import com.apprentice.rpg.gui.MainFrame;
import com.apprentice.rpg.util.WindowUtils;
import com.google.inject.Inject;

public final class ApprenticeDesktopControl implements IApprenticeDesktopControl {

	private static Logger LOG = Logger.getLogger(ApprenticeDesktopControl.class);

	private final ApprenticeDesktop desktop;
	private final ApplicationConfiguration config;

	@Inject
	public ApprenticeDesktopControl(final ApplicationConfiguration config) {
		this.config = config;
		this.desktop = new ApprenticeDesktop();
		setStoredBackground();
		desktop.addMouseListener(new BackgroundChangePopupMenu(this));
	}

	@Override
	public void attachDesktopToMainFrame(final MainFrame mainFrame) {
		mainFrame.setDesktop(desktop);
	}

	@Override
	public Rectangle getDesktopBounds() {
		return desktop.getBounds();
	}

	@Override
	public void setBackgroundColor(final Color color) {
		desktop.setBackgroundImage(null);
		desktop.setBackgroundColor(color);
		// also set it in the config
		config.setBackgroundColor(color);
	}

	@Override
	public void setBackgroundImage(final String imagePath) {
		try {
			final Image background = WindowUtils.getImageFromPath(imagePath);
			desktop.setBackgroundImage(background);
		} catch (final ApprenticeEx e) {
			WindowUtils.showErrorMessage("Could not set background image  \"" + imagePath + "\"!\nError: "
				+ e.getMessage() + "\nUsing default background");
			setDefaultDesktopBackground();
		}
	}

	@Override
	public void setDefaultDesktopBackground() {
		try {
			desktop.setBackgroundImage(WindowUtils.getDefaultDesktopBackgroundImage());
			config.setBackgroundDefaultImage();
		} catch (final ApprenticeEx e) {
			setBackgroundColor(Color.BLACK);
			LOG.error("Could not set default background image!\nError: " + e.getMessage());
		}
	}

	/**
	 * Sets the default image as the background
	 * 
	 * @throws ApprenticeEx
	 *             If something was wrong with the image
	 */
	@Override
	public void setStoredBackground() {
		final DesktopBackgroundType storedType = config.getChoseBackgroundType();
		switch (storedType) {
		case DEFAULT:
			setDefaultDesktopBackground();
			break;
		case COLOR:
			setBackgroundColor(config.getBackgroundColor());
			break;
		case IMAGE:
			final String backgroundImagePath = config.getBackgroundImagePath();
			try {
				setBackgroundImage(backgroundImagePath);
			} catch (final ApprenticeEx e) {
				WindowUtils.showErrorMessage("Stored background image \"" + backgroundImagePath
					+ "\" does not point to a valid image.\nUsing default background");
				setDefaultDesktopBackground();
			}
		}
	}

}
