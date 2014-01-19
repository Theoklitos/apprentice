package com.apprentice.rpg.gui.database;

import com.apprentice.rpg.dao.DatabaseConnection;
import com.apprentice.rpg.gui.ControllableView;
import com.google.inject.Inject;

/**
 * Control for the {@link DatabaseSettingsFrame}
 * 
 * @author theoklitos
 * 
 */
public final class DatabaseSettingsFrameControl implements IDatabaseSettingsFrameControl {

	private DatabaseSettingsFrame view;
	private final DatabaseConnection databaseConnection;

	@Inject
	public DatabaseSettingsFrameControl(final DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	@Override
	public String getDatabaseLocation() {
		return databaseConnection.getLocation();
	}

	@Override
	public void setView(final ControllableView view) {
		this.view = (DatabaseSettingsFrame) view;
	}

}
