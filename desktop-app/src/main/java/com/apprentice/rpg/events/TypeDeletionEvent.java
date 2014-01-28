package com.apprentice.rpg.events;

import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.body.Type;

/**
 * Fired when a {@link Type} has been deleted
 * 
 * @author theoklitos
 * 
 */
public class TypeDeletionEvent extends DatabaseDeletionEvent {

	public TypeDeletionEvent(final Nameable type) {
		super(type);
	}

}
