package com.apprentice.rpg.gui.vault.type;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.apprentice.rpg.dao.ItemNotFoundEx;
import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.events.BodyPartDeletionEvent;
import com.apprentice.rpg.events.BodyPartUpdateEvent;
import com.apprentice.rpg.events.DatabaseDeletionEvent;
import com.apprentice.rpg.events.DatabaseUpdateEvent;
import com.apprentice.rpg.events.TypeDeletionEvent;
import com.apprentice.rpg.events.TypeUpdateEvent;
import com.apprentice.rpg.gui.ControllableView;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.parsing.ParsingEx;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.parsing.exportImport.ExportConfigurationObject;
import com.apprentice.rpg.parsing.exportImport.IDatabaseImporterExporter;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

/**
 * Controls the {@link exportItems}
 * 
 * @author theoklitos
 * 
 */
public class TypeAndBodyPartFrameControl implements ITypeAndBodyPartFrameControl {

	private static Logger LOG = Logger.getLogger(TypeAndBodyPartFrameControl.class);

	private final Vault vault;
	private TypeAndBodyPartFrame view;
	private final ApprenticeEventBus eventBus;
	private final IDatabaseImporterExporter dbImporterExporter;

	@Inject
	public TypeAndBodyPartFrameControl(final Vault vault, final ApprenticeEventBus eventBus,
			final IDatabaseImporterExporter dbImporterExporter) {
		this.vault = vault;
		this.eventBus = eventBus;
		this.dbImporterExporter = dbImporterExporter;
	}

	@Subscribe
	public void bodyPartNameDelete(final BodyPartDeletionEvent event) {
		view.removeBodyPartsInView(event.getPayload());
	}

	@Override
	public void createOrUpdate(final Nameable item, final ItemType type) throws NameAlreadyExistsEx {
		final String message = vault.exists(item) ? "Updated " : "Created ";
		DatabaseUpdateEvent<?> event = null;
		switch (type) {
		case BODY_PART:
			event = new BodyPartUpdateEvent((BodyPart) item);
			break;
		case TYPE:
			event = new TypeUpdateEvent((IType) item);
			break;
		}
		vault.update(item);
		LOG.info(message + type + " \"" + item.getName() + "\".");
		eventBus.postEvent(event);
		view.refreshFromModel(true);
	}

	@Override
	public void deleteByName(final String name, final ItemType type) throws ItemNotFoundEx {
		DatabaseDeletionEvent<?> event = null;
		Nameable item = null;
		switch (type) {
		case BODY_PART:
			item = getBodyPartForName(name);
			event = new BodyPartDeletionEvent((BodyPart) item);
			break;
		case TYPE:
			item = getTypeForName(name);
			event = new TypeDeletionEvent((IType) item);
			break;
		}
		if (vault.delete(item)) {
			LOG.info("Deleted " + type + " \"" + item.getName() + "\".");
			eventBus.postEvent(event);
			view.refreshFromModel(true);
		}
	}

	@Override
	public boolean doesTypeNameExist(final String name) {
		return vault.doesNameExist(name, IType.class);
	}

	@Override
	public void exportForConfiguration(final ExportConfigurationObject config) throws ItemNotFoundEx, ParsingEx {
		dbImporterExporter.export(config);
	}

	@Override
	public BodyPart getBodyPartForName(final String bodyPartName) throws ItemNotFoundEx {
		for (final BodyPart bodyPart : getBodyParts()) {
			if (bodyPart.getName().equals(bodyPartName)) {
				return bodyPart;
			}
		}
		throw new ItemNotFoundEx("Body part \"" + bodyPartName + "\" does not exist.");
	}

	@Override
	public Collection<BodyPart> getBodyParts() {
		return vault.getAll(BodyPart.class);
	}

	@Override
	public IType getTypeForName(final String typeName) throws ItemNotFoundEx {
		for (final IType type : getTypes()) {
			if (type.getName().equals(typeName)) {
				return type;
			}
		}
		throw new ItemNotFoundEx("Type \"" + typeName + "\" does not exist.");
	}

	@Override
	public Nameable getTypeOrBodyPartForName(final String name, final ItemType type) throws ItemNotFoundEx {
		switch (type) {
		case BODY_PART:
			return getBodyPartForName(name);
		case TYPE:
			return getTypeForName(name);
		}
		throw new ItemNotFoundEx();
	}

	@Override
	public Collection<IType> getTypes() {
		return vault.getAll(IType.class);
	}

	@Override
	public Vault getVault() {
		return vault;
	}

	@Override
	public void importFromFile(final String fileLocation) throws ParsingEx {
		dbImporterExporter.importFrom(fileLocation);
		view.refreshFromModel(true);
	}

	@Override
	public void setView(final ControllableView view) {
		this.view = (TypeAndBodyPartFrame) view;
	}

}
