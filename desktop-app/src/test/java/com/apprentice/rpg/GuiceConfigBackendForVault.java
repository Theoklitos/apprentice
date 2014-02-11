package com.apprentice.rpg;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.parsing.ApprenticeParser;
import com.apprentice.rpg.parsing.JsonParser;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter;
import com.apprentice.rpg.parsing.exportImport.IDatabaseImporterExporter;
import com.google.inject.AbstractModule;

/**
 * can be used to set mocked (or stubs) for DB classes
 * 
 * @author theoklitos
 * 
 */
public final class GuiceConfigBackendForVault extends AbstractModule {

	private final DatabaseConnection databaseConnection;
	private final Vault vault;

	public GuiceConfigBackendForVault(final DatabaseConnection database, final Vault vault) {
		this.databaseConnection = database;
		this.vault = vault;
	}

	@Override
	protected void configure() {
		bind(DatabaseConnection.class).toInstance(databaseConnection);
		bind(Vault.class).toInstance(vault);
		bind(ApprenticeParser.class).to(JsonParser.class);
		bind(IDatabaseImporterExporter.class).to(DatabaseImporterExporter.class);
	}

}
