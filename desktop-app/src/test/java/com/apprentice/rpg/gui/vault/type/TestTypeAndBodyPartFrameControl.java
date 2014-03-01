package com.apprentice.rpg.gui.vault.type;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.events.type.BodyPartDeletionEvent;
import com.apprentice.rpg.events.type.BodyPartUpdateEvent;
import com.apprentice.rpg.events.type.TypeDeletionEvent;
import com.apprentice.rpg.events.type.TypeUpdateEvent;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.parsing.exportImport.ExportConfigurationObject;
import com.apprentice.rpg.parsing.exportImport.IDatabaseImporterExporter;
import com.google.common.collect.Sets;

/**
 * tests for the {@link TypeAndBodyPartFrameControl}
 * 
 * @author theoklitos
 * 
 */
public final class TestTypeAndBodyPartFrameControl {

	private TypeAndBodyPartFrameControl control;
	private Mockery mockery;
	private Vault vault;
	private ApprenticeEventBus eventBus;
	private IDatabaseImporterExporter dbio;
	private ITypeAndBodyPartFrame view;
	private Collection<IType> types;
	private Collection<BodyPart> parts;

	@Test
	public void createBodyPart() {
		final BodyPart part = new BodyPart("head");
		mockery.checking(new Expectations() {
			{
				oneOf(vault).exists(part);
				will(returnValue(false));
				oneOf(vault).update(part);
				oneOf(eventBus).postEvent(with(any(BodyPartUpdateEvent.class)));
				oneOf(view).refreshFromModel();
			}
		});
		control.createOrUpdate(part, ItemType.BODY_PART);
	}

	@Test
	public void deleteBodyPart() {
		final BodyPart part = new BodyPart("head");
		parts.add(part);
		mockery.checking(new Expectations() {
			{
				oneOf(vault).delete(part);
				will(returnValue(true));
				oneOf(eventBus).postEvent(with(any(BodyPartDeletionEvent.class)));
				oneOf(view).refreshFromModel();
			}
		});
		control.deleteByName("head", ItemType.BODY_PART);
	}

	@Test
	public void deleteType() {
		final IType type = mockery.mock(IType.class);
		types.add(type);
		mockery.checking(new Expectations() {
			{
				allowing(type).getName();
				will(returnValue("test type"));
				oneOf(vault).delete(type);
				will(returnValue(true));
				oneOf(eventBus).postEvent(with(any(TypeDeletionEvent.class)));
				oneOf(view).refreshFromModel();
			}
		});
		control.deleteByName("test type", ItemType.TYPE);
	}

	@Test
	public void exportAndImport() {
		final String fileLocation = "testfile";
		final ExportConfigurationObject config = new ExportConfigurationObject();
		config.setFileLocation(fileLocation);

		mockery.checking(new Expectations() {
			{
				oneOf(dbio).export(config);
				oneOf(dbio).importFrom(fileLocation);
				oneOf(view).refreshFromModel();
			}
		});
		control.exportForConfiguration(config);
		control.importFromFile(fileLocation);
	}

	@Test
	public void getItems() {
		assertEquals(types, control.getTypes());
		assertEquals(parts, control.getBodyParts());
	}

	@Before
	public void setup() {
		mockery = new Mockery();
		vault = mockery.mock(Vault.class);
		eventBus = mockery.mock(ApprenticeEventBus.class);
		dbio = mockery.mock(IDatabaseImporterExporter.class);
		view = mockery.mock(ITypeAndBodyPartFrame.class);
		types = Sets.newHashSet();
		parts = Sets.newHashSet();
		mockery.checking(new Expectations() {
			{
				allowing(vault).getAllNameables(IType.class);
				will(returnValue(types));
				allowing(vault).getAllNameables(BodyPart.class);
				will(returnValue(parts));
			}
		});
		control = new TypeAndBodyPartFrameControl(vault, eventBus, dbio);
		control.setView(view);
	}

	@After
	public void teardown() {
		mockery.assertIsSatisfied();
	}

	@Test
	public void updateType() {
		final IType type = mockery.mock(IType.class);
		mockery.checking(new Expectations() {
			{
				oneOf(type).getName();
				will(returnValue("test type"));

				oneOf(vault).exists(type);
				will(returnValue(false));
				oneOf(vault).update(type);
				oneOf(eventBus).postEvent(with(any(TypeUpdateEvent.class)));
				oneOf(view).refreshFromModel();
			}
		});
		control.createOrUpdate(type, ItemType.TYPE);
	}

}
