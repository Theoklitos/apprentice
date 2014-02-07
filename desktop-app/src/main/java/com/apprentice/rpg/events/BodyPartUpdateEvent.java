package com.apprentice.rpg.events;

import com.apprentice.rpg.model.body.BodyPart;

/**
 * When a {@link BodyPart} is changed
 * 
 * @author theoklitos
 *
 */
public class BodyPartUpdateEvent extends DatabaseUpdateEvent<BodyPart> {

	public BodyPartUpdateEvent(final BodyPart updatedBodyPart) {
		super(updatedBodyPart);
	}

}
