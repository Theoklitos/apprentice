package com.apprentice.rpg.gui.vault.type;

import java.util.List;

import org.apache.log4j.Logger;

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
import com.apprentice.rpg.model.body.Type;
import com.google.inject.Inject;

/**
 * Controls the {@link TypeAndBodyPartFrame}
 * 
 * @author theoklitos
 * 
 */
public class TypeAndBodyPartFrameControl implements ITypeAndBodyPartFrameControl {

	/**
	 * used to facilitate method calls from the frame
	 * 
	 * @author theoklitos
	 * 
	 */
	public enum ItemType {
		TYPE,
		BODY_PART;

		@Override
		public String toString() {
			if(name().equals(ItemType.TYPE)) {
				return "Type";
			} else {
				return "Body Part";
			}
		}
	}

	private static Logger LOG = Logger.getLogger(TypeAndBodyPartFrameControl.class);

	private final Vault vault;
	private TypeAndBodyPartFrame view;
	private final ApprenticeEventBus eventBus;

	@Inject
	public TypeAndBodyPartFrameControl(final Vault vault, final ApprenticeEventBus eventBus) {
		this.vault = vault;
		this.eventBus = eventBus;
	}

	@Override
	public void create(final String name, final ItemType itemType) {
		Object result = null;
		DatabaseUpdateEvent event = null;
		switch (itemType) {
		case BODY_PART:
			final BodyPart newPart = new BodyPart(name);
			event = new BodyPartUpdateEvent(newPart);
			result = newPart;
			break;
		case TYPE:
			final IType newType = new Type(name);
			event = new TypeUpdateEvent(newType);
			result = newType;
			break;
		}
		vault.create(result);
		eventBus.postEvent(event);
		view.refreshFromModel();
	}

	@Override
	public void delete(final Nameable item, final ItemType itemType) {	
		DatabaseDeletionEvent event = null;
		switch (itemType) {
		case BODY_PART:			
			event = new BodyPartDeletionEvent(item);			
			break;
		case TYPE:			
			event = new TypeDeletionEvent(item);			
			break;
		}
		if (vault.delete(item)) {
			eventBus.postEvent(event);
			view.refreshFromModel();
		}
	}

	@Override
	public List<BodyPart> getBodyParts() {
		return vault.getAll(BodyPart.class);
	}

	@Override
	public List<IType> getTypes() {
		return vault.getAll(IType.class);
	}

	@Override
	public void setView(final ControllableView view) {
		this.view = (TypeAndBodyPartFrame) view;
	}

	@Override
	public void update(final Nameable item, final ItemType type) {
		DatabaseUpdateEvent event = null;
		switch (type) {
		case BODY_PART:
			event = new BodyPartUpdateEvent(item);
			break;
		case TYPE:
			event = new TypeUpdateEvent(item);
			break;
		}
		vault.update(item);
		LOG.info("Updated " + item.getClass().getSimpleName() + ".");
		eventBus.postEvent(event);
		view.refreshFromModel();
	}

}
