package com.apprentice.rpg.gui.desktop;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

import org.apache.log4j.Logger;

import com.apprentice.rpg.config.ApprenticeConfiguration;
import com.apprentice.rpg.config.ApprenticeConfiguration.DesktopBackgroundType;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.ApprenticeEx;
import com.google.inject.Inject;

public final class ApprenticeDesktopControl implements IApprenticeDesktopControl {

	private static Logger LOG = Logger.getLogger(ApprenticeDesktopControl.class);

	private final ApprenticeConfiguration configuration;
	private ApprenticeDesktop view;
	private final IGlobalWindowState globalState;

	@Inject
	public ApprenticeDesktopControl(final IGlobalWindowState globalState, final ApprenticeConfiguration configuration) {
		this.globalState = globalState;
		this.configuration = configuration;
	}

	@Override
	public void add(final ApprenticeInternalFrame<?> internalFrame) {
		if(view != null) {
			view.add(internalFrame);
		}
	}
	
	@Override
	public Rectangle getDesktopBounds() {
		return view.getBounds();
	}

	@Override
	public void setBackgroundColor(final Color color) {
		view.setBackgroundImage(null);
		view.setBackgroundColor(color);
		// also store it in the config
		configuration.setBackgroundColor(color);
		LOG.info("Background set to " + color.toString().toLowerCase() + ".");
	}

	/**
	 * Sets the default image as the background
	 * 
	 * @throws ApprenticeExr
	 *             If something was wrong with the image
	 */
	@Override
	public void setBackgroundFromConfig() {
		final DesktopBackgroundType storedType = configuration.getChosenBackgroundType();
		switch (storedType) {
		case DEFAULT:
			setDefaultDesktopBackground();
			break;
		case COLOR:
			setBackgroundColor(configuration.getBackgroundColor());
			break;
		case IMAGE:
			final String backgroundImagePath = configuration.getBackgroundImagePath();
			try {
				setBackgroundImage(backgroundImagePath);
			} catch (final ApprenticeEx e) {
				globalState.getWindowUtils().showErrorMessage(
						"Stored background image \"" + backgroundImagePath
							+ "\" does not point to a valid image.\nUsing default background.");
				setDefaultDesktopBackground();
			}
		}
	}

	@Override
	public void setBackgroundImage(final String imagePath) {
		try {
			final Image background = globalState.getWindowUtils().getImageFromPath(imagePath);
			view.setBackgroundImage(background);
			// also store it in the config
			configuration.setBackgroundImagePath(imagePath);
			LOG.info("Background image set to " + imagePath);
		} catch (final ApprenticeEx e) {
			globalState.getWindowUtils().showErrorMessage(
					"Could not set background image  \"" + imagePath + "\"!\nReason: " + e.getMessage()
						+ "\nUsing default background.");
			setDefaultDesktopBackground();
		}
	}

	@Override
	public void setDefaultDesktopBackground() {
		try {
			view.setBackgroundImage(globalState.getWindowUtils().getDefaultDesktopBackgroundImage());
			configuration.setBackgroundDefaultImage();
		} catch (final ApprenticeEx e) {
			setBackgroundColor(Color.BLACK);
			// this is very weird, it should be logged
			LOG.error("Could not set default background image!\nReason: " + e.getMessage());
		}
	}

	@Override
	public void setView(final ApprenticeDesktop view) {
		this.view = view;
		final BackgroundChangePopupMenu popupMenu = new BackgroundChangePopupMenu(this.view, this);
		this.view.addMouseListener(popupMenu);
	}

}
