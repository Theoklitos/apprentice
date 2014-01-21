package com.apprentice.rpg.dao.vault;

import com.apprentice.rpg.dao.DatabaseConnection;
import com.apprentice.rpg.model.body.Type;

/**
 * Contains all the types of beings in this rog system, for example, "Humanoid", "Winged Humanoid", "Quadropod",
 * etc
 * 
 * @author theoklitos
 * 
 */
public final class TypeVault {

	private final DatabaseConnection connection;

	public TypeVault(final DatabaseConnection connection) {
		this.connection = connection;
	}
	
	/**
	 * doh! TODO
	 */
	public final Type getTypeForName(final String name) {
		return null;
	}
}
