package com.apprentice.rpg.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.config.ApprenticeConfiguration;
import com.apprentice.rpg.config.ApprenticeConfiguration.DesktopBackgroundType;
import com.apprentice.rpg.dao.ItemAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.gui.GlobalWindowState;
import com.apprentice.rpg.gui.IGlobalWindowState;
import com.apprentice.rpg.gui.WindowState;
import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.model.guice.GuiceConfigBackend;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Testing if the database saves and loads as intented
 * 
 * @author theoklitos
 * 
 */
public final class ITDatabaseSaveLoad {

	private static Logger LOG = Logger.getLogger(ITDatabaseSaveLoad.class);

	private final static String TMP_DIR = System.getProperty("java.io.tmpdir");
	private final static File TEMP_DB_FILE = new File(TMP_DIR, "apprentice_test_db");
	private DatabaseConnection database;
	private Vault vault;
	private DataFactory factory;

	@Test(expected = ItemAlreadyExistsEx.class)
	public void cannotCreateSameUniqueObject() {
		final IPlayerCharacter pc = factory.getPlayerCharacter();
		vault.create(pc);
		vault.update(pc);
		vault.create(pc);
	}

	@Test
	public void saveLoadConfiguration() {
		final ApprenticeConfiguration config = new ApprenticeConfiguration();
		config.setBackgroundColor(Color.RED);
		database.save(config);
		database.closeDB();

		LOG.info("Opening database and retrieving config...");
		database.openDB();
		final ApprenticeConfiguration loaded = database.load(ApprenticeConfiguration.class).get(0);
		assertEquals(Color.RED, loaded.getBackgroundColor());
		assertEquals(DesktopBackgroundType.COLOR, loaded.getChosenBackgroundType());
		LOG.info("Success.");
	}

	@Test
	public void saveLoadPlayerCharacter() {
		final IPlayerCharacter pc = factory.getPlayerCharacter();
		LOG.info("Storing player characater...");
		vault.update(pc);
		database.closeDB();

		database.openDB();
		LOG.info("Retrieving and comparing same characater...");
		final IPlayerCharacter loaded = vault.getUniqueNamedResult(pc.getName(), IPlayerCharacter.class);
		assertEquals(pc, loaded);
		LOG.info("Success.");
	}

	@Test
	public void saveLoadType() {		
		final IType human = null;
		//final IType type2 = new Type("type2", parts);
		//vault.create(type1);		;
		database.closeDB();

		database.openDB();
		final List<IType> retrieved = vault.getAll(IType.class);
		assertThat(retrieved.size(), is(2));
		assertTrue(retrieved.contains(human));
		//assertTrue(retrieved.contains(type2));
	}

	@Test
	public void saveLoadWindowState() {
		final IGlobalWindowState windowState = new GlobalWindowState();
		final String windowName = "window1";
		final Rectangle bounds = new Rectangle(10, 5, 200, 1000);
		final boolean isOpen = true;
		windowState.updateWindow(windowName, bounds, isOpen);
		database.save(windowState);
		database.closeDB();

		LOG.info("Opening database and retrieving window state...");
		database.openDB();
		final IGlobalWindowState loaded = database.load(IGlobalWindowState.class).get(0);
		final WindowState expected = new WindowState(bounds, isOpen);
		assertEquals(expected, loaded.getWindowState(windowName).getContent());
		LOG.info("Success.");
	}

	@Before
	public void setup() {
		if (TEMP_DB_FILE.exists() && !TEMP_DB_FILE.delete()) {
			fail("Temporary database \"" + TEMP_DB_FILE
				+ "\" exists! Delete and fix this before attempting the integration tests.");
		}
		final Injector injector = Guice.createInjector(new GuiceConfigBackend(TEMP_DB_FILE.toString()));
		database = injector.getInstance(DatabaseConnection.class);
		vault = injector.getInstance(Vault.class);
		factory = new DataFactory();
	}

	@After
	public void teardown() {
		if (TEMP_DB_FILE.exists()) {
			database.closeDB();
			TEMP_DB_FILE.delete();
			LOG.info("Deleted " + TEMP_DB_FILE);
		}
		System.out.println("\n"); // for better logging
	}
}
