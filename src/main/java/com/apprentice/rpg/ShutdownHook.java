package com.apprentice.rpg;

import org.apache.log4j.Logger;

import com.apprentice.rpg.config.ApplicationConfiguration;
import com.apprentice.rpg.dao.DatabaseConnection;
import com.google.inject.Inject;

public final class ShutdownHook {

	private static Logger LOG = Logger.getLogger(ShutdownHook.class);

	private final DatabaseConnection database;
	private final ApplicationConfiguration config;

	@Inject
	public ShutdownHook(final DatabaseConnection database, final ApplicationConfiguration config) {
		this.database = database;
		this.config = config;
	}

	public void shutdownGracefully() {
// database.storeConfig(config);
// database.closeDB();
// stopListeners(listenersToStop);
		LOG.info("Killing driz'zt...");
		LOG.info("Apprentice has been shut down. Have a nice day!");
	}

}
