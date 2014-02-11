package com.apprentice.rpg.model.body;

import com.apprentice.rpg.model.Nameable;

/**
 * All beings (PCs and NPCs) comprise of several body parts. Many body parts create a type.
 * 
 * @author theoklitos
 * 
 */
public final class BodyPart extends BaseApprenticeObject implements Nameable {

	public BodyPart(final String name) {
		super(name);
	}

	@Override
	public String toString() {
		return getName();
	}

}
