package com.apprentice.rpg.events;

import com.apprentice.rpg.model.body.IType;

/**
 * Anything that happened in regards to the {@link IType}s
 * 
 * @author theoklitos
 * 
 */
public class TypeModificationEvent extends DatabaseModificationEvent<IType> {

	public TypeModificationEvent(final IType object) {
		super(object);
	}

}
