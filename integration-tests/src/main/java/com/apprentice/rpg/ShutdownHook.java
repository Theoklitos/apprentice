package com.apprentice.rpg;

import org.apache.log4j.Logger;

import com.apprentice.rpg.config.IApplicationConfiguration;
import com.apprentice.rpg.dao.DatabaseConnection;
import com.apprentice.rpg.gui.IGlobalWindowState;
import com.google.inject.Inject;

public final class ShutdownHook implements IShutdownHook {

	private static Logger LOG = Logger.getLogger(ShutdownHook.class);

	private final DatabaseConnection database;
	private final IApplicationConfiguration config;
	private final IGlobalWindowState globalWindowState;

	@Inject
	public ShutdownHook(final DatabaseConnection database, final IApplicationConfiguration config,
			final IGlobalWindowState globalWindowState) {
		this.database = database;
		this.config = config;
		this.globalWindowState = globalWindowState;
	}

	public void shutdownGracefully() {
		database.save(globalWindowState);
		database.save(config);
		database.closeDB();
		LOG.debug("Killing driz'zt...");
		LOG.debug("Apprentice has been shut down. Have a nice day!");
	}

}
