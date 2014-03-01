package com.apprentice.rpg.model.weapon;



/**
 * A prototype weapon that PCs get handed out. When a PC aquires a weapon, this prototype is encapsulated and
 * referenced inside a {@link WeaponInstance}
 * 
 * @author theoklitos
 * 
 */
public interface WeaponPrototype extends IWeapon, Cloneable {
	// marker
}
