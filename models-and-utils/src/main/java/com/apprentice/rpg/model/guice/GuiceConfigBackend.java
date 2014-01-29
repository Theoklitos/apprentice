package com.apprentice.rpg.model.guice;

import com.apprentice.rpg.dao.DataAccessObjectForAll;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.database.Db4oConnection;
import com.apprentice.rpg.parsing.ApprenticeParser;
import com.apprentice.rpg.parsing.JsonParser;
import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ext.Db4oException;
import com.google.inject.Binder;
import com.google.inject.Module;

public final class GuiceConfigBackend implements Module {

	private final EmbeddedObjectContainer objectContainer;
	private final String databaseLocation;

	public GuiceConfigBackend(final EmbeddedObjectContainer objectContainer, final String databaseLocation) {
		this.objectContainer = objectContainer;		
		this.databaseLocation = databaseLocation;		
	}
	
	public GuiceConfigBackend(final String databaseLocation) throws Db4oException {
		this(Db4oEmbedded.openFile(databaseLocation), databaseLocation);
	}

	@Override
	public void configure(final Binder binder) {
		final DatabaseConnection db4oImpl = new Db4oConnection(objectContainer, databaseLocation);
		binder.bind(DatabaseConnection.class).toInstance(db4oImpl);
		final Vault vault = new DataAccessObjectForAll(db4oImpl);
		binder.bind(Vault.class).toInstance(vault);
		binder.bind(ApprenticeParser.class).to(JsonParser.class);
	}

}
