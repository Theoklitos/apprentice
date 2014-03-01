package com.apprentice.rpg.events.type;

import com.apprentice.rpg.model.body.IType;

/**
 * Fired when a {@link IType} is changed
 * 
 * @author theoklitos
 * 
 */
public final class TypeUpdateEvent extends DatabaseModificationEvent<IType> implements UpdateEvent<IType> {

	public TypeUpdateEvent(final IType newType) {
		super(newType);
	}

}
