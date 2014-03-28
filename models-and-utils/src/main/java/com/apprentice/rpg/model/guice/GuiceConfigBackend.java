package com.apprentice.rpg.model.guice;

import com.apprentice.rpg.dao.DataAccessObjectForAll;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.dao.time.ModificationTimeVault;
import com.apprentice.rpg.dao.time.TimeToNameableMapper;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.database.Db4oConnection;
import com.apprentice.rpg.parsing.ApprenticeParser;
import com.apprentice.rpg.parsing.JsonParser;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter;
import com.apprentice.rpg.parsing.exportImport.IDatabaseImporterExporter;
import com.apprentice.rpg.random.ApprenticePseudoRandom;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.apprentice.rpg.strike.EffectManager;
import com.apprentice.rpg.strike.IEffectManager;
import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ext.Db4oException;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;

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
		binder.bind(IDatabaseImporterExporter.class).to(DatabaseImporterExporter.class);
		binder.bind(ApprenticeRandom.class).to(ApprenticePseudoRandom.class);
		binder.bind(IEffectManager.class).to(EffectManager.class).asEagerSingleton();
	}

	@Singleton
	@Provides
	@Inject
	public ModificationTimeVault getModificationTimeVault(final Vault vault) {
		return vault.getUniqueObjectFromDB(TimeToNameableMapper.class);
	}

}
