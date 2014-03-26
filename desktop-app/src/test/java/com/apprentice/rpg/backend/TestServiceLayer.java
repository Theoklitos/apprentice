package com.apprentice.rpg.backend;

import static org.junit.Assert.assertEquals;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * tests for the {@link ServiceLayer}
 * 
 * @author theoklitos
 * 
 */
public final class TestServiceLayer {

	private Vault vault;
	private ApprenticeEventBus eventBus;
	private IGlobalWindowState globalWindowState;
	private ServiceLayer serviceLayer;
	private Mockery mockery;
	private Nameable item;

	@Test
	public void create() {
		mockery.checking(new Expectations() {
			{
				oneOf(vault).exists(item);
				will(returnValue(true));
				oneOf(vault).update(item);
				oneOf(eventBus).postUpdateEvent(item);
			}
		});
		serviceLayer.createOrUpdate(item);
	}

	@Test
	public void createUniqueName() {
		mockery.checking(new Expectations() {
			{
				oneOf(vault).doesNameExist(item.getName(), item.getClass());
				will(returnValue(false));
				oneOf(vault).exists(item);
				will(returnValue(false));
				oneOf(vault).update(item);
				oneOf(eventBus).postCreationEvent(item);
			}
		});
		serviceLayer.createOrUpdateUniqueName(item);
	}

	@Test
	public void deleteNameable() {
		final boolean result = true;
		mockery.checking(new Expectations() {
			{
				oneOf(vault).getUniqueNamedResult(item.getName(), item.getClass());
				will(returnValue(item));
				oneOf(vault).delete(item);
				will(returnValue(result));
				oneOf(eventBus).postDeleteEvent(item);
			}
		});
		assertEquals(result, serviceLayer.deleteNameable(item.getName(), ItemType.BODY_PART));
	}

	@Test
	public void getUpdateTime() {
		final String result = "1:2:3";
		mockery.checking(new Expectations() {
			{
				oneOf(vault).getUniqueNamedResult(item.getName(), item.getClass());
				will(returnValue(item));
				oneOf(vault).getPrettyUpdateTime(item);
				will(returnValue(result));
			}
		});
		assertEquals(result, serviceLayer.getLastUpdateTime(item.getName(), ItemType.BODY_PART));
	}

	@Test
	public void renameNamebale() {
		final String newName = "newName";
		mockery.checking(new Expectations() {
			{
				oneOf(vault).getUniqueNamedResult(item.getName(), item.getClass());
				will(returnValue(item));
				oneOf(vault).update(new BodyPart(newName));
				oneOf(eventBus).postUpdateEvent(item);
			}
		});
		serviceLayer.renameNamebale(item.getName(), newName, ItemType.BODY_PART);
	}

	@Before
	public void setup() {
		mockery = new Mockery();
		eventBus = mockery.mock(ApprenticeEventBus.class);
		globalWindowState = mockery.mock(IGlobalWindowState.class);
		vault = mockery.mock(Vault.class);
		item = new BodyPart("testBodyPart");
		serviceLayer = new ServiceLayer(vault, eventBus, globalWindowState);
	}

	@Test
	public void update() {
		mockery.checking(new Expectations() {
			{
				oneOf(vault).exists(item);
				will(returnValue(false));
				oneOf(vault).update(item);
				oneOf(eventBus).postCreationEvent(item);
			}
		});
		serviceLayer.createOrUpdate(item);
	}

	@Test
	public void updateUniqueName() {
		mockery.checking(new Expectations() {
			{
				oneOf(vault).doesNameExist(item.getName(), item.getClass());
				will(returnValue(false));
				oneOf(vault).exists(item);
				will(returnValue(true));
				oneOf(vault).update(item);
				oneOf(eventBus).postUpdateEvent(item);
			}
		});
		serviceLayer.createOrUpdateUniqueName(item);
	}

	@Test(expected = NameAlreadyExistsEx.class)
	public void updateUniqueNameAlreadyExists() {
		mockery.checking(new Expectations() {
			{
				oneOf(vault).doesNameExist(item.getName(), item.getClass());
				will(returnValue(true));
			}
		});
		serviceLayer.createOrUpdateUniqueName(item);
	}

}
