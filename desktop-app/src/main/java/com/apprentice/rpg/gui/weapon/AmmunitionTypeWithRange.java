package com.apprentice.rpg.gui.weapon;

import com.apprentice.rpg.model.weapon.AmmunitionType;
import com.apprentice.rpg.model.weapon.Range;
import com.apprentice.rpg.util.Checker;

/**
 * Wrapper around an {@link AmmunitionType} and a {@link Range}
 * 
 * @author theoklitos
 * 
 */
public final class AmmunitionTypeWithRange {

	private final AmmunitionType ammoType;
	private final Range range;

	public AmmunitionTypeWithRange(final AmmunitionType ammoType, final Range range) {
		Checker.checkNonNull("Ammo type or range is missing!", false, ammoType, range);
		this.ammoType = ammoType;
		this.range = range;
	}

	public AmmunitionType getAmmoType() {
		return ammoType;
	}

	public Range getRange() {
		return range;
	}

	@Override
	public String toString() {
		return ammoType.getName() + range;
	}
}
