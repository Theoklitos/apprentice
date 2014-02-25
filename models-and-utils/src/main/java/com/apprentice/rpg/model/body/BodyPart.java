package com.apprentice.rpg.model.body;


/**
 * All beings (PCs and NPCs) comprise of several body parts. Many body parts create a type.
 * 
 * @author theoklitos
 * 
 */
public final class BodyPart extends BaseApprenticeObject {

	public BodyPart(final String name) {
		super(name);
	}

	@Override
	public String toString() {
		return getName();
	}

}
