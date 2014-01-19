package com.apprentice.rpg.gui.database;

import com.apprentice.rpg.gui.ControlForView;

/**
 * Control for the {@link DatabaseSettingsFrame}
 * 
 * @author theoklitos
 * 
 */
public interface IDatabaseSettingsFrameControl extends ControlForView {

	/**
	 * Returns the db file URI
	 */
	String getDatabaseLocation();

}
