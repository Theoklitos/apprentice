package com.apprentice.rpg.events;

import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.body.BodyPart;

public class BodyPartUpdateEvent extends DatabaseUpdateEvent {

	public BodyPartUpdateEvent(final Nameable bodyPart) {
		super(bodyPart);
	}

	/**
	 * returns the (new or updated) object that triggered this event
	 */
	@Override
	public BodyPart getPayload() {
		return (BodyPart) super.getPayload();
	}

}
