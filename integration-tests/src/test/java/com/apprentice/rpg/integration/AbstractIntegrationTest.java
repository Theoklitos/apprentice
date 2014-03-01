package com.apprentice.rpg.integration;

import static org.junit.Assert.fail;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import com.apprentice.rpg.dao.DataAccessObjectForAll;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.dao.simple.NameableVault;
import com.apprentice.rpg.dao.time.TimeToNameableMapper;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.armor.IArmorPiece;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.model.guice.GuiceConfigBackend;
import com.apprentice.rpg.model.weapon.WeaponPrototype;
import com.apprentice.rpg.parsing.ApprenticeParser;
import com.apprentice.rpg.strike.StrikeType;
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
		setExessiveLogging(false);

		vault.delete(factory);
		database.commit();
		// System.out.println(vault.getAll(IPlayerCharacter.class));
		// System.out.println(vault.getAll(BodyPart.class));
		// System.out.println(vault.getAll(StrikeType.class));
		// System.out.println(vault.getAll(IType.class));
		// System.out.println(vault.getAll(Weapon.class));
		// System.out.println(vault.getAll(ArmorPiece.class)); TODO
		// for (final Object object : vault.getAll(Object.class)) {
		// vault.delete(object);
		// }
		// database.commit();

		// assertEquals(0, vault.getAll(Object.class).size());
		setExessiveLogging(true);
	}

	/**
	 * uses the Logger to print some debug lines about what Nameables exist in the given vault
	 */
	public void logVaultInfo(final NameableVault vault) {
		LOG.debug("=====================" + vault.getClass().getSimpleName() + "=====================");
		LOG.debug("Size: " + vault.getAllNameables().size());
		LOG.debug("BodyPart: " + vault.getAllNameables(BodyPart.class).size());
		for (final BodyPart part : vault.getAllNameables(BodyPart.class)) {
			LOG.debug("Name: " + part.getName() + ", hash: " + part.hashCode());
		}
		//LOG.debug("BodyPart List: " + vault.getAllNameables(BodyPart.class));
		LOG.debug("StrikeType: " + vault.getAllNameables(StrikeType.class).size());
		LOG.debug("Type: " + vault.getAllNameables(IType.class).size());
		LOG.debug("Weapons: " + vault.getAllNameables(WeaponPrototype.class).size());
		LOG.debug("ArmorPiece: " + vault.getAllNameables(IArmorPiece.class).size());
		LOG.debug("PlayerCharacter: " + vault.getAllNameables(IPlayerCharacter.class).size());
		LOG.debug("==========================================================================");
	}

	/**
	 * will store everything in the {@link DataFactory} inside the {@link Vault}
	 */
	public void saveAllFactoryDataToDatbase() {
		setExessiveLogging(false);
		for (final Nameable nameable : factory.getAllNameables()) {
			vault.update(nameable);
		}
		setExessiveLogging(true);
	}

	/**
	 * sets several annoying loggers to OFF or ALL
	 */
	public void setExessiveLogging(final boolean enable) {
		Level level;
		if (enable) {
			level = Level.ALL;
		} else {
			level = Level.OFF;
		}
		Logger.getLogger(TimeToNameableMapper.class).setLevel(level);
		Logger.getLogger(DataAccessObjectForAll.class).setLevel(level);
	}

}
