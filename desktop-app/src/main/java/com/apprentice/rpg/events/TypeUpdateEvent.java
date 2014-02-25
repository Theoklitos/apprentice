package com.apprentice.rpg.events;

import com.apprentice.rpg.model.body.IType;

/**
 * Fired when a {@link IType} is changed
 * 
 * @author theoklitos
 * 
 */
public final class TypeUpdateEvent extends TypeModificationEvent {

	public TypeUpdateEvent(final IType newType) {
		super(newType);
	}

}
