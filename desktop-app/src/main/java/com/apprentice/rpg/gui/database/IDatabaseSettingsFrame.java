package com.apprentice.rpg.gui.database;

import java.util.List;

import com.apprentice.rpg.gui.ControllableView;

/**
 * Used to see information about the databas and also set the database's location
 * 
 * @author theoklitos
 *
 */
public interface IDatabaseSettingsFrame extends ControllableView{

	/**
	 * replaces the contets of the "description" panel with the given strings.Every element is one line.
	 */
	void setDatabaseDescription(List<String> description);

}
