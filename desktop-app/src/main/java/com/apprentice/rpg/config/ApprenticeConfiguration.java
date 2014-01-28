package com.apprentice.rpg.config;

import java.awt.Color;
import java.io.File;

import org.apache.log4j.Logger;

import com.apprentice.rpg.util.Checker;

/**
 * App-wide configuration settings
 * 
 */
public class ApprenticeConfiguration implements IApprenticeConfiguration {

	public enum DesktopBackgroundType {
		IMAGE,
		COLOR,
		DEFAULT
	}

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(ApprenticeConfiguration.class);

	/**
	 * this is not the db4o but a text config file that is safely tucked away and stores the path to the db4o
	 * file
	 */
	public final static String TEXT_CONFIG_FILENAME = "apprenticeProperties";
	public final static String DATABASE_FILENAME = "vault";
	public static final String OWNER_EMAIL = "thechristodoulou@gmail.com";

	private static String getApplicationFolder() {
		return System.getProperty("user.dir");
	}

	/**
	 * this returns the same folder as the jar is in, plus the DATABASE_FILENAME
	 */
	public static String getDefaultDatabasePath() {
		return new File(getApplicationFolder(), DATABASE_FILENAME).getAbsolutePath();
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

	public DesktopBackgroundType getChosenBackgroundType() {
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
	}

	public void setBackgroundDefaultImage() {
		desktopBackgroundType = DesktopBackgroundType.DEFAULT;
	}

	public void setBackgroundImagePath(final String path) {
		Checker.checkNonNull("Background image path is not set", true, path);
		desktopBackgroundType = DesktopBackgroundType.IMAGE;
		backgroundImagePath = path;
	}

	@Override
	public String toString() {
		return "Database at: " + getDefaultDatabasePath() + ". Creator is " + OWNER_EMAIL;
	}
}
