package com.apprentice.rpg.gui.weapon;

import com.apprentice.rpg.gui.ControllableView;
import com.apprentice.rpg.model.weapon.WeaponPrototype;

/**
 * Edits or creates {@link WeaponPrototype}s
 * 
 * @author theoklitos
 * 
 */
public interface IWeaponFrame extends ControllableView {

	/**
	 * Will display the given weapon's stats in the frame for editing
	 */
	void setWeaponForEditing(WeaponPrototype newWeapon);

}
