package com.apprentice.rpg.events.type;

import com.apprentice.rpg.model.body.IType;

/**
 * Anything that happened in regards to the {@link IType}s
 * 
 * @author theoklitos
 * 
 */
public class TypeCreationEvent extends DatabaseModificationEvent<IType> implements CreationEvent<IType> {

	public TypeCreationEvent(final IType object) {
		super(object);
	}

}
