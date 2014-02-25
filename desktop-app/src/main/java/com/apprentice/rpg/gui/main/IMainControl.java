package com.apprentice.rpg.gui.main;

import com.apprentice.rpg.dao.Vault;

public interface IMainControl {

	/**
	 * returns a reference to the {@link Vault}
	 */
	Vault getVault();

	/**
	 * calls the shutdown hook
	 */
	void shutdownGracefully();

}
