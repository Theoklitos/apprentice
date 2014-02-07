package com.apprentice.rpg.events.database;

import org.apache.log4j.Logger;

import com.apprentice.rpg.events.BodyPartDeletionEvent;
import com.apprentice.rpg.events.BodyPartUpdateEvent;
import com.google.common.eventbus.Subscribe;

/**
 * The data synchronizer subscribes to various database change events, and refreshes other parts of the
 * database accordingly.
 * 
 * @author theoklitos
 * 
 */
public final class DataSynchronizer implements IDataSynchronizer {

	private static Logger LOG = Logger.getLogger(DataSynchronizer.class);

	@Subscribe
	public void bodyPartNameDelete(final BodyPartDeletionEvent event) {

	}

	@Subscribe
	public void bodyPartNameUpdate(final BodyPartUpdateEvent event) {

	}
}
