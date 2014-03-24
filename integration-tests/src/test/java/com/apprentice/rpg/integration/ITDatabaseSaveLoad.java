package com.apprentice.rpg.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.apprentice.rpg.config.ApprenticeConfiguration;
import com.apprentice.rpg.config.ApprenticeConfiguration.DesktopBackgroundType;
import com.apprentice.rpg.dao.ItemAlreadyExistsEx;
import com.apprentice.rpg.gui.weapon.WeaponFrame;
import com.apprentice.rpg.gui.windowState.GlobalWindowState;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.gui.windowState.WindowState;
import com.apprentice.rpg.gui.windowState.WindowStateIdentifier;
import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;

/**
 * Testing if the database saves and loads as intented
 * 
 * @author theoklitos
 * 
 */
public final class ITDatabaseSaveLoad extends AbstractIntegrationTest {

	private static Logger LOG = Logger.getLogger(ITDatabaseSaveLoad.class);

	@Test(expected = ItemAlreadyExistsEx.class)
	public void cannotCreateSameUniqueObject() {
		final IPlayerCharacter pc = factory.getPlayerCharacter2();
		vault.create(pc);
		vault.update(pc);
		vault.create(pc);
	}

	@Test
	public void changeDatabaseLocation() {
		saveAllFactoryDataToDatbase();
		final File tempDbFile2 = new File(TMP_DIR, "apprentice_test_db_2");
		LOG.info("Closing and opening secondary (empty) database...");
		database.setDatabase(tempDbFile2.getAbsolutePath());
		assertEquals(0, vault.getAllNameables().size());
		LOG.info("Switching back to the original database...");
		database.setDatabase(TEMP_DB_FILE.getAbsolutePath());
		assertEquals(11, vault.getAllNameables(BodyPart.class).size() + vault.getAllNameables(IType.class).size());
		LOG.info("Success.");
		tempDbFile2.delete();
	}

	@Test
	public void saveLoadConfiguration() {
		final ApprenticeConfiguration config = new ApprenticeConfiguration();
		config.setBackgroundColor(Color.RED);
		database.saveAndCommit(config);
		database.closeDB();

		LOG.info("Opening database and retrieving config...");
		database.openDB();
		final ApprenticeConfiguration loaded = database.load(ApprenticeConfiguration.class).iterator().next();
		assertEquals(Color.RED, loaded.getBackgroundColor());
		assertEquals(DesktopBackgroundType.COLOR, loaded.getChosenBackgroundType());
		LOG.info("Success.");
	}

	@Test
	public void saveLoadPlayerCharacter() {
		final IPlayerCharacter pc = factory.getPlayerCharacter1();
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
		vault.create(factory.getTypes().get(0));
		vault.create(factory.getTypes().get(1));
		database.closeDB();

		database.openDB();
		final Collection<IType> retrieved = vault.getAllNameables(IType.class);
		assertThat(retrieved.size(), is(2));
		assertTrue(retrieved.contains(factory.getTypes().get(0)));
		assertTrue(retrieved.contains(factory.getTypes().get(1)));
	}

	@Test
	public void saveLoadWindowState() {
		final IGlobalWindowState windowState = new GlobalWindowState(null);
		final WindowStateIdentifier identifier = new WindowStateIdentifier(WeaponFrame.class, "longsword");
		final Rectangle bounds = new Rectangle(10, 5, 200, 1000);
		final boolean isOpen = true;
		windowState.setWindowState(identifier, bounds, isOpen);
		database.saveAndCommit(windowState);
		database.closeDB();

		LOG.info("Opening database and retrieving window state...");
		database.openDB();
		final IGlobalWindowState loaded = database.load(IGlobalWindowState.class).iterator().next();
		final WindowState expected = new WindowState(bounds, isOpen);
		assertEquals(expected, loaded.getWindowState(identifier).getContent());
		LOG.info("Success.");
	}

}
