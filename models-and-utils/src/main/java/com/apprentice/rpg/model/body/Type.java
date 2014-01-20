package com.apprentice.rpg.model.body;

import java.util.List;

/**
 * All beings (PCs and NPCs) comprise of several body parts. Many body parts create a type.
 * 
 * @author theoklitos
 * 
 */
public final class Type {

	private final String name;
	private final List<BodyPart> parts;	
	
	public Type(final String name, final List<BodyPart> parts) {
		this.name = name;
		this.parts = parts;		
	}

}
