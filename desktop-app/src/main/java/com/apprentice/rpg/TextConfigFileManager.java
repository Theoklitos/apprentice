package com.apprentice.rpg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.apprentice.rpg.config.ApprenticeConfiguration;
import com.apprentice.rpg.util.Box;
import com.apprentice.rpg.util.OSValidator;

/**
 * reads and writes properties into a text config file
 * 
 * @author theoklitos
 * 
 */
public final class TextConfigFileManager {

	private static Logger LOG = Logger.getLogger(TextConfigFileManager.class);

	public static final String DATABASE_PROPERTY_IN_CONFIG = "database";

	/**
	 * depending on the OS, retruns the expected config file location. Will return an empty box if the OS is
	 * not supported
	 */
	public static Box<File> getConfigFileBasedOnOS() {
		File expectedDbFile = null;
		if (OSValidator.isWindows()) {
			expectedDbFile =
				new File(System.getenv("APPDATA") + "\\apprentice\\" + ApprenticeConfiguration.TEXT_CONFIG_FILENAME);
		} else if (OSValidator.isUnix()) {
			expectedDbFile = new File(System.getProperty("user.home") + "/." + ApprenticeConfiguration.TEXT_CONFIG_FILENAME);
		}
		if (expectedDbFile != null) {
			return Box.with(expectedDbFile);
		} else {
			return Box.empty();
		}
	}

	/**
	 * write the database location in a text config/properties file, the location of which depends on the OS
	 */
	public static void writeDatabaseLocation(final String dabtabasePath) {
		final Box<File> configFileBox = getConfigFileBasedOnOS();
		if (configFileBox.hasContent()) {
			final File configFile = configFileBox.getContent();
			if (!configFile.exists()) {
				try {
					configFile.createNewFile();
				} catch (final Exception e) {
					LOG.error("Could not write property file: " + e.getMessage());
				}
			}
			final Properties properties = new Properties();
			properties.setProperty(DATABASE_PROPERTY_IN_CONFIG, dabtabasePath);
			try {
				final OutputStream out = new FileOutputStream(configFile);
				properties.store(out, "Apprentice uses this file to store the location of its database.");
				LOG.info("Wrote database location to " + configFile);
			} catch (final FileNotFoundException e) {
				// this cannot happen, we check for existence before
				LOG.error("Config file not found: " + e.getMessage());
			} catch (final IOException e) {
				LOG.error("Error while writing config file: " + e.getMessage());
			}
		} else {
			LOG.warn("Unsupported OS! User is using " + OSValidator.getOperatingSystem() + ". Config file not written.");
		}
	}
}
