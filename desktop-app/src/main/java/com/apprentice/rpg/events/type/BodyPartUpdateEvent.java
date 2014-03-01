package com.apprentice.rpg.events.type;

import com.apprentice.rpg.model.body.BodyPart;

/**
 * When a {@link BodyPart} is changed
 * 
 * @author theoklitos
 * 
 */
public class BodyPartUpdateEvent extends DatabaseModificationEvent<BodyPart> implements UpdateEvent<BodyPart> {

	public BodyPartUpdateEvent(final BodyPart updatedBodyPart) {
		super(updatedBodyPart);
	}

}
