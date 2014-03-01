package com.apprentice.rpg.gui.weapon;

import org.apache.log4j.Logger;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.gui.AbstractControlForView;
import com.apprentice.rpg.model.weapon.WeaponPrototype;
import com.google.inject.Inject;

/**
 * Controls the {@link IWeaponFrame}
 * 
 * @author theoklitos
 * 
 */
public class WeaponFrameControl extends AbstractControlForView implements IWeaponFrameControl {

	private static Logger LOG = Logger.getLogger(WeaponFrameControl.class);

	private IWeaponFrame view;

	@Inject
	public WeaponFrameControl(final Vault vault, final ApprenticeEventBus eventBus) {
		super(vault, eventBus);
	}

	@Override
	public void createOrUpdateWeapon(final WeaponPrototype weapon) throws NameAlreadyExistsEx {
		final boolean isNew = !getVault().exists(weapon);
		getVault().update(weapon);
		if (isNew) {
			LOG.info("New weapon " + weapon.getName() + " created");
			getEventBus().postCreationEvent(weapon);
		} else {
			LOG.info("Weapon " + weapon.getName() + " updated");
			getEventBus().postUpdateEvent(weapon);
		}
		view.setWeaponForEditing(weapon);
	}

	@Override
	public void setView(final IWeaponFrame view) {
		this.view = view;
	}

}
