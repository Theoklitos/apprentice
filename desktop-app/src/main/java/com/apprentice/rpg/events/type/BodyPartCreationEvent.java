package com.apprentice.rpg.events.type;

import com.apprentice.rpg.model.body.BodyPart;

/**
 * When a {@link BodyPart} has been created
 * 
 * @author theoklitos
 * 
 */
public class BodyPartCreationEvent extends DatabaseModificationEvent<BodyPart> implements CreationEvent<BodyPart> {

	public BodyPartCreationEvent(final BodyPart deletedBodyPart) {
		super(deletedBodyPart);
	}
}
