package com.apprentice.rpg.guice;

import com.apprentice.rpg.ShutdownHook;
import com.apprentice.rpg.dao.DatabaseConnection;
import com.apprentice.rpg.dao.Db4oConnection;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public final class GuiceBackendConfig implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.bind(DatabaseConnection.class).to(Db4oConnection.class);
		binder.bind(ShutdownHook.class).in(Scopes.SINGLETON);
	}
}
