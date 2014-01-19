package com.apprentice.rpg.dao.vault;

import com.apprentice.rpg.dao.DatabaseConnection;
import com.apprentice.rpg.model.PlayerCharacter;
import com.google.inject.Inject;

/**
 * Stores all the {@link PlayerCharacter}s
 * 
 * @author theoklitos
 *
 */
public final class PlayerCharacterVault {

	private final DatabaseConnection connection;

	@Inject
	public PlayerCharacterVault(final DatabaseConnection connection) {
		this.connection = connection;
	}
	
	/**
	 * Stores the given character. 
	 */
	public void saveCharacter(final PlayerCharacter playerCharacter) {
		connection.save(playerCharacter);
	}
}
