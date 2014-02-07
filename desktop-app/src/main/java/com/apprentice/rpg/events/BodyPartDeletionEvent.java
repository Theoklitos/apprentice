package com.apprentice.rpg.events;

import com.apprentice.rpg.model.body.BodyPart;

/**
 * When a {@link BodyPart} has been deleted
 * 
 * @author theoklitos
 * 
 */
public class BodyPartDeletionEvent extends DatabaseDeletionEvent<BodyPart> {

	public BodyPartDeletionEvent(final BodyPart deletedBodyPart) {
		super(deletedBodyPart);
	}

}
