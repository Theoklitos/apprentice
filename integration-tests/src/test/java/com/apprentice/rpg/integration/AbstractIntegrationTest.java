package com.apprentice.rpg.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.model.guice.GuiceConfigBackend;
import com.apprentice.rpg.parsing.ApprenticeParser;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * setup/teardown for all the integration tests
 * 
 * @author theoklitos
 * 
 */
public abstract class AbstractIntegrationTest {

	private static Logger LOG = Logger.getLogger(AbstractIntegrationTest.class);

	public final static String TMP_DIR = System.getProperty("java.io.tmpdir");
	public final static File TEMP_DB_FILE = new File(TMP_DIR, "apprentice_test_db");
	public DatabaseConnection database;
	public Vault vault;
	public DataFactory factory;
	public ApprenticeParser parser;

	@Before
	public void abstractSetup() {
		if (TEMP_DB_FILE.exists() && !TEMP_DB_FILE.delete()) {
			fail("Temporary database \"" + TEMP_DB_FILE
				+ "\" exists! Delete and fix this before attempting the integration tests.");
		}
		final Injector injector = Guice.createInjector(new GuiceConfigBackend(TEMP_DB_FILE.toString()));
		database = injector.getInstance(DatabaseConnection.class);
		vault = injector.getInstance(Vault.class);
		parser = injector.getInstance(ApprenticeParser.class);
		factory = new DataFactory();
	}

	@After
	public void abstractTeardown() {
		if (TEMP_DB_FILE.exists()) {
			database.closeDB();
			TEMP_DB_FILE.delete();
			LOG.info("Deleted " + TEMP_DB_FILE);
		}
		System.out.println("\n"); // for better logging
	}

	/**
	 * Deletes everything in the database
	 */
	public void emptyDatabase() {
		for (final Object object : vault.getAll(Object.class)) {
			vault.delete(object);
		}
		database.commit();
		assertEquals(0, vault.getAll(Object.class).size());
	}

	/**
	 * will store everything in the {@link DataFactory} inside the {@link Vault}
	 */
	public void saveAllFactoryDataToDatbase() {
		for (final IType type : factory.getTypes()) {
			vault.create(type);
		}
		for (final BodyPart part : factory.getBodyParts()) {
			vault.update(part);
		}
	}
}
