package com.apprentice.rpg.dao.repository;

import com.apprentice.rpg.dao.DatabaseConnection;
import com.apprentice.rpg.model.body.Type;

/**
 * Contains all the types of beings in this rog system, for example, "Humanoid", "Winged Humanoid", "Quadropod",
 * etc
 * 
 * @author theoklitos
 * 
 */
public final class TypeRepository {

	private final DatabaseConnection connection;

	public TypeRepository(final DatabaseConnection connection) {
		this.connection = connection;
	}
	
	/**
	 * 
	 */
	public final Type getTypeForName(final String name) {
		return null;
	}
}
