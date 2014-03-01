package com.apprentice.rpg.gui.character.player.creation;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.apprentice.rpg.dao.ItemAlreadyExistsEx;
import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.events.type.TypeCreationEvent;
import com.apprentice.rpg.gui.AbstractControlForView;
import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.model.body.IType;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

/**
 * Control for the {@link NewPlayerCharacterFrame}
 * 
 * @author theoklitos
 * 
 */
public final class NewPlayerCharacterFrameControl extends AbstractControlForView implements INewPlayerCharacterFrameControl {

	private static Logger LOG = Logger.getLogger(NewPlayerCharacterFrameControl.class);

	private INewPlayerCharacterFrame view;
	private final Vault vault;

	@Inject
	public NewPlayerCharacterFrameControl(final Vault vault, final ApprenticeEventBus eventBus) {
		super(vault,eventBus);
		this.vault = vault;
	}

	@Override
	public void createCharacter(final PlayerCharacter character) throws NameAlreadyExistsEx, ItemAlreadyExistsEx {
		if (vault.doesNameExist(character.getName(), IPlayerCharacter.class)) {
			throw new NameAlreadyExistsEx();
		} else {
			vault.create(character);
			LOG.info("New player created: " + character.getName() + "!");
			getEventBus().postUpdateEvent(character);			
		}
	}

	@Override
	public Collection<IType> getAllTypes() {
		return vault.getAll(IType.class);
	}

	@Override
	public void setView(final INewPlayerCharacterFrame view) {
		this.view = view;
	}

	@Subscribe
	public void someTypeHasChanged(final TypeCreationEvent typeEvent) {		
		view.refreshTypeDropdown();
	}	
}
