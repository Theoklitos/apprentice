package com.apprentice.rpg.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.apprentice.rpg.config.ApprenticeConfiguration;
import com.apprentice.rpg.config.ApprenticeConfiguration.DesktopBackgroundType;
import com.apprentice.rpg.dao.ItemAlreadyExistsEx;
import com.apprentice.rpg.gui.GlobalWindowState;
import com.apprentice.rpg.gui.IGlobalWindowState;
import com.apprentice.rpg.gui.WindowState;
import com.apprentice.rpg.model.IPlayerCharacter;
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
		final IPlayerCharacter pc = factory.getPlayerCharacter();
		vault.create(pc);
		vault.update(pc);
		vault.create(pc);
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
		vault.create(factory.getTypes().get(0));
		vault.create(factory.getTypes().get(1));
		database.closeDB();

		database.openDB();
		final Collection<IType> retrieved = vault.getAll(IType.class);
		assertThat(retrieved.size(), is(2));
		assertTrue(retrieved.contains(factory.getTypes().get(0)));
		assertTrue(retrieved.contains(factory.getTypes().get(1)));
	}

	@Test
	public void saveLoadWindowState() {
		final IGlobalWindowState windowState = new GlobalWindowState(null);
		final String windowName = "window1";
		final Rectangle bounds = new Rectangle(10, 5, 200, 1000);
		final boolean isOpen = true;
		windowState.updateWindow(windowName, bounds, isOpen);
		database.saveAndCommit(windowState);
		database.closeDB();

		LOG.info("Opening database and retrieving window state...");
		database.openDB();
		final IGlobalWindowState loaded = database.load(IGlobalWindowState.class).iterator().next();
		final WindowState expected = new WindowState(bounds, isOpen);
		assertEquals(expected, loaded.getWindowState(windowName).getContent());
		LOG.info("Success.");
	}

}
