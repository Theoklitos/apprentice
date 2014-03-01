package com.apprentice.rpg.events.type;

import com.apprentice.rpg.model.body.BodyPart;

/**
 * When a {@link BodyPart} has been deleted
 * 
 * @author theoklitos
 * 
 */
public class BodyPartDeletionEvent extends DatabaseModificationEvent<BodyPart> implements DeletionEvent<BodyPart> {

	public BodyPartDeletionEvent(final BodyPart deletedBodyPart) {
		super(deletedBodyPart);
	}

}
