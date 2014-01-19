package com.apprentice.rpg.guice;

import com.apprentice.rpg.config.ApplicationConfiguration;
import com.apprentice.rpg.config.IApplicationConfiguration;
import com.apprentice.rpg.dao.DatabaseConnection;
import com.apprentice.rpg.dao.Db4oConnection;
import com.apprentice.rpg.parsing.ApprenticeParser;
import com.apprentice.rpg.parsing.JsonParser;
import com.db4o.EmbeddedObjectContainer;
import com.google.inject.Binder;
import com.google.inject.Module;

public final class GuiceConfigBackend implements Module {

	private final EmbeddedObjectContainer objectContainer;
	private final String databaseLocation;

	public GuiceConfigBackend(final EmbeddedObjectContainer objectContainer, final String databaseLocation) {
		this.objectContainer = objectContainer;
		this.databaseLocation = databaseLocation;
	}

	@Override
	public void configure(final Binder binder) {
		final DatabaseConnection db4oImpl = new Db4oConnection(objectContainer, databaseLocation);		
		final ApplicationConfiguration configuration = db4oImpl.getConfiguration();
		binder.bind(IApplicationConfiguration.class).toInstance(configuration);		
		binder.bind(DatabaseConnection.class).toInstance(db4oImpl);
		binder.bind(ApprenticeParser.class).to(JsonParser.class);
	}

}
