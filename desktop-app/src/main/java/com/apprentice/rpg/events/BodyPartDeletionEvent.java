package com.apprentice.rpg.events;

import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.body.BodyPart;

/**
 * When a {@link BodyPart} has been deleted
 * 
 * @author theoklitos
 *
 */
public class BodyPartDeletionEvent extends DatabaseDeletionEvent {

	public BodyPartDeletionEvent(final Nameable bodyPart) {
		super(bodyPart);
	}

}
