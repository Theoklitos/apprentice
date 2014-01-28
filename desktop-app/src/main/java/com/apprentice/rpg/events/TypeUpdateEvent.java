package com.apprentice.rpg.events;

import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.body.IType;

public class TypeUpdateEvent extends DatabaseUpdateEvent {

	public TypeUpdateEvent(final Nameable type) {
		super(type);
	}

	/**
	 * returns the (new or updated) object that triggered this event
	 */
	@Override
	public IType getPayload() {
		return (IType) super.getPayload();
	}

}
