package com.apprentice.rpg.guice;

import com.apprentice.rpg.config.IApplicationConfiguration;
import com.apprentice.rpg.dao.DatabaseConnection;
import com.apprentice.rpg.dao.Db4oConnection;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;

public final class GuiceBackendConfig implements Module {

	@Override
	public void configure(final Binder binder) {		
		binder.bind(DatabaseConnection.class).to(Db4oConnection.class).in(Scopes.SINGLETON);
	}

	@Provides
	@Singleton
	@Inject
	public IApplicationConfiguration getApplicationConfiguration(final DatabaseConnection databaseConnection) {
		return databaseConnection.getConfiguration();
	}
}
