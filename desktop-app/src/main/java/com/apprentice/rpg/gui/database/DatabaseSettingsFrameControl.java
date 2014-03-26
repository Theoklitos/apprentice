package com.apprentice.rpg.gui.database;

import java.io.File;

import org.apache.log4j.Logger;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.config.ITextConfigFileManager;
import com.apprentice.rpg.database.ApprenticeDatabaseEx;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.gui.AbstractControlForView;
import com.google.inject.Inject;

/**
 * Control for the {@link DatabaseSettingsFrame}
 * 
 * @author theoklitos
 * 
 */
public final class DatabaseSettingsFrameControl extends AbstractControlForView<IDatabaseSettingsFrame> implements
		IDatabaseSettingsFrameControl {

	private static Logger LOG = Logger.getLogger(DatabaseSettingsFrameControl.class);

	private final ITextConfigFileManager textfileManager;
	private final DatabaseConnection databaseConnection;

	@Inject
	public DatabaseSettingsFrameControl(final IServiceLayer serviceLayer, final DatabaseConnection databaseConnection,
			final ITextConfigFileManager textfileManager) {
		super(serviceLayer);
		this.databaseConnection = databaseConnection;
		this.textfileManager = textfileManager;
	}

	@Override
	public void changeDatabaseLocation(final String databaseLocation) throws ApprenticeDatabaseEx {
		if (!new File(databaseLocation).exists()) {
			throw new ApprenticeDatabaseEx("File " + databaseLocation + " does not exist.");
		}
		changeDatabaseLocationUnsafe(databaseLocation);
	}

	/**
	 * changes the database without checking if the file exists first. Called directly for testing
	 */
	protected void changeDatabaseLocationUnsafe(final String databaseLocation) throws ApprenticeDatabaseEx {
		databaseConnection.setDatabase(databaseLocation);
		textfileManager.writeDatabaseLocation(databaseLocation);
		getEventBus().postShutdownEvent(false);
		getEventBus().postShowFrameEvent(DatabaseSettingsFrame.class);
		LOG.info("Using new database " + databaseLocation);
	}

	@Override
	public String getDatabaseLocation() {
		return databaseConnection.getLocation();
	}

}
