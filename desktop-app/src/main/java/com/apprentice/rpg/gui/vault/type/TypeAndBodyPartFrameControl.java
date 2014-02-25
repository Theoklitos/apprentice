package com.apprentice.rpg.gui.vault.type;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.apprentice.rpg.dao.ItemNotFoundEx;
import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.dao.time.ModificationTimeVault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.events.BodyPartDeletionEvent;
import com.apprentice.rpg.events.BodyPartUpdateEvent;
import com.apprentice.rpg.events.DatabaseModificationEvent;
import com.apprentice.rpg.events.TypeDeletionEvent;
import com.apprentice.rpg.events.TypeUpdateEvent;
import com.apprentice.rpg.gui.AbstractControlForView;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.parsing.ParsingEx;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.parsing.exportImport.ExportConfigurationObject;
import com.apprentice.rpg.parsing.exportImport.IDatabaseImporterExporter;
import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

/**
 * Controls the {@link exportItems}
 * 
 * @author theoklitos
 * 
 */
public class TypeAndBodyPartFrameControl extends AbstractControlForView implements ITypeAndBodyPartFrameControl {

	private static Logger LOG = Logger.getLogger(TypeAndBodyPartFrameControl.class);

	private final Collection<BodyPart> bodyPartBuffer;
	private final Collection<IType> typeBuffer;
	private boolean isBufferUpdated;

	private final Vault vault;
	private ITypeAndBodyPartFrame view;
	private final ApprenticeEventBus eventBus;
	private final IDatabaseImporterExporter dbImporterExporter;

	@Inject
	public TypeAndBodyPartFrameControl(final Vault vault, final ApprenticeEventBus eventBus,
			final IDatabaseImporterExporter dbImporterExporter) {
		super(vault, eventBus);
		this.vault = vault;
		this.eventBus = eventBus;
		this.dbImporterExporter = dbImporterExporter;

		bodyPartBuffer = Sets.newHashSet();
		typeBuffer = Sets.newHashSet();
	}

	@Subscribe
	public void bodyPartNameDelete(final BodyPartDeletionEvent event) {
		view.removeBodyPartsInView(event.getPayload());
	}

	@Override
	public void createOrUpdate(final Nameable item, final ItemType type) throws NameAlreadyExistsEx {
		final String message = vault.exists(item) ? "Updated " : "Created ";
		DatabaseModificationEvent<?> event = null;
		switch (type) {
		case BODY_PART:
			event = new BodyPartUpdateEvent((BodyPart) item);
			break;
		case TYPE:
			event = new TypeUpdateEvent((IType) item);
			break;
		default:
			return;
		}
		vault.update(item);
		isBufferUpdated = false;
		LOG.info(message + type + " " + item.getName() + ".");
		eventBus.postEvent(event);
		view.refreshFromModel();
	}

	@Override
	public void deleteByName(final String name, final ItemType type) throws ItemNotFoundEx {
		DatabaseModificationEvent<?> event = null;		
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
		default:
			return;
		}
		if (vault.delete(item)) {
			isBufferUpdated = false;
			LOG.info("Deleted " + type + " " + item.getName());
			eventBus.postEvent(event);
			view.refreshFromModel();
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
		if (!isBufferUpdated) {
			updateBuffer();
		}
		return bodyPartBuffer;
	}

	@Override
	public ApprenticeEventBus getEventBus() {
		return eventBus;
	}

	@Override
	public String getLastUpdateTime(final String typeName, final ItemType type) {
		try {
			return vault.getPrettyUpdateTime(getTypeOrBodyPartForName(typeName, type));
		} catch (final ItemNotFoundEx e) {
			return ModificationTimeVault.NO_TIMING_DESCRIPTION;
		}
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
		default:
			// nothing, exception will be thrown
		}
		throw new ItemNotFoundEx();
	}

	@Override
	public Collection<IType> getTypes() {
		if (!isBufferUpdated) {
			updateBuffer();
		}
		return typeBuffer;
	}

	@Override
	public Vault getVault() {
		return vault;
	}

	@Override
	public void importFromFile(final String fileLocation) throws ParsingEx {
		dbImporterExporter.importFrom(fileLocation);
		isBufferUpdated = false;
		view.refreshFromModel();
	}

	/**
	 * called by tests
	 */
	protected void setIsBufferUpdated(final boolean isBufferUpdated) {
		this.isBufferUpdated = isBufferUpdated;
	}

	@Override
	public void setView(final ITypeAndBodyPartFrame view) {
		this.view = view;
	}

	/**
	 * hits the database
	 */
	private void updateBuffer() {
		if (!isBufferUpdated) {
			bodyPartBuffer.clear();
			bodyPartBuffer.addAll(vault.getAllNameables(BodyPart.class));
			typeBuffer.clear();
			typeBuffer.addAll(vault.getAllNameables(IType.class));
			isBufferUpdated = true;
		}
	}

}
