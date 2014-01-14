package com.apprentice.rpg.config;

import java.awt.Color;
import java.io.File;

import org.apache.log4j.Logger;

import com.apprentice.rpg.util.Checker;

/**
 * App-wide configuration settings
 * 
 */
public class ApplicationConfiguration {

	public enum DesktopBackgroundType {
		IMAGE,
		COLOR,
		DEFAULT
	}

	private static Logger LOG = Logger.getLogger(ApplicationConfiguration.class);

	public final static String DATABASE_FILENAME = "spellbook";
	public static final String OWNER_EMAIL = "thechristodoulou@gmail.com";

	protected static String getApplicationFolder() {
		return System.getProperty("user.dir");
	}

	public static File getDatabaseFile() {
		return new File(getApplicationFolder(), DATABASE_FILENAME);
	}

	private DesktopBackgroundType desktopBackgroundType = null;

	private String backgroundImagePath = null;
	private Color backgroundColor = null;

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public String getBackgroundImagePath() {
		return backgroundImagePath;
	}

	public DesktopBackgroundType getChoseBackgroundType() {
		if (desktopBackgroundType == null) {
			return DesktopBackgroundType.DEFAULT;
		} else {
			return desktopBackgroundType;
		}
	}

	public void setBackgroundColor(final Color color) {
		Checker.checkNonNull("No color is given for background color", true, color);
		desktopBackgroundType = DesktopBackgroundType.COLOR;
		backgroundColor = color;
		LOG.info("Background set to color [" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "]");
	}

	public void setBackgroundDefaultImage() {
		desktopBackgroundType = DesktopBackgroundType.DEFAULT;
		LOG.info("Background image set to default");
	}

	public void setBackgroundImagePath(final String path) {
		Checker.checkNonNull("Background image path is not set", true, path);
		desktopBackgroundType = DesktopBackgroundType.IMAGE;
		backgroundImagePath = path;
		LOG.info("Background image set to file \"" + path + "\"");
	}

	@Override
	public String toString() {
		return "Database at: " + getDatabaseFile() + ". Creator is " + OWNER_EMAIL;
	}
}
