package com.apprentice.rpg.strike;

import com.apprentice.rpg.model.body.BaseApprenticeObject;

/**
 * Represents what kind of strike this is, i.e. slashing, bludgeoning, piercing etc
 * 
 * @author theoklitos
 * 
 */
public final class StrikeType extends BaseApprenticeObject {

	public StrikeType(final String name) {		
		super(name);
	}
	
	@Override
	public String toString() {
		return getName();
	}

}
