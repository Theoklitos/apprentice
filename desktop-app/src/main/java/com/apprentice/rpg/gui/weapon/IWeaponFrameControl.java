package com.apprentice.rpg.gui.weapon;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.gui.ControlWithVault;
import com.apprentice.rpg.gui.ControlForView;
import com.apprentice.rpg.model.weapon.WeaponPrototype;

/**
 * Controls the {@link IWeaponFrame}
 * 
 * @author theoklitos
 * 
 */
public interface IWeaponFrameControl extends ControlForView<IWeaponFrame>, ControlWithVault {

	/**
	 * Checks for uniqueness of name and if so, stores/updates the weapon
	 * 
	 * @throws NameAlreadyExistsEx
	 */
	void createOrUpdateWeapon(WeaponPrototype newWeapon) throws NameAlreadyExistsEx;

}
