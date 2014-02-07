package com.apprentice.rpg.events;

import com.apprentice.rpg.model.body.IType;

/**
 * Fired when a {@link IType} is changed
 * 
 * @author theoklitos
 * 
 */
public class TypeUpdateEvent extends DatabaseUpdateEvent<IType> {

	public TypeUpdateEvent(final IType newType) {
		super(newType);
	}

}
