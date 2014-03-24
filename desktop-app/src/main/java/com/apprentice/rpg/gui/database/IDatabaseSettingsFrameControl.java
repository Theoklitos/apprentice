package com.apprentice.rpg.gui.database;

import com.apprentice.rpg.database.ApprenticeDatabaseEx;
import com.apprentice.rpg.gui.ControlForView;

/**
 * Control for the {@link DatabaseSettingsFrame}
 * 
 * @author theoklitos
 * 
 */
public interface IDatabaseSettingsFrameControl extends ControlForView<IDatabaseSettingsFrame> {

	/**
	 * shuts down the current database, closes all windows/frames, and re-opens the database
	 * 
	 * @throws ApprenticeDatabaseEx
	 *             if anything goes wrong
	 */
	void changeDatabaseLocation(String databaseLocation) throws ApprenticeDatabaseEx;

	/**
	 * Returns the db file URI
	 */
	String getDatabaseLocation();

}
