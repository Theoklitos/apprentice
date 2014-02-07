package com.apprentice.rpg.events;

import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.body.Type;

/**
 * Fired when a {@link Type} has been deleted
 * 
 * @author theoklitos
 * 
 */
public class TypeDeletionEvent extends DatabaseDeletionEvent<IType> {

	public TypeDeletionEvent(final IType deletedType) {
		super(deletedType);
	}

}
