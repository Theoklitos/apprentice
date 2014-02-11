package com.apprentice.rpg.config;

import java.io.File;

import com.apprentice.rpg.util.Box;

/**
 * reads and writes properties into a text config file
 * 
 * @author theoklitos
 * 
 */
public interface ITextConfigFileManager {

	/**
	 * depending on the OS, retruns the expected config file location. Will return an empty box if the OS is
	 * not supported
	 */
	Box<File> getConfigFileBasedOnOS();

	/**
	 * write the database location in a text config/properties file, the location of which depends on the OS
	 */
	void writeDatabaseLocation(String dabtabasePath);

}
