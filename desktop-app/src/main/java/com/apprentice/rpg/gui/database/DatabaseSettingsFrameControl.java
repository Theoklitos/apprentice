package com.apprentice.rpg.gui.database;

import java.util.List;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.events.DatabaseModificationEvent;
import com.apprentice.rpg.gui.ControllableView;
import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

/**
 * Control for the {@link DatabaseSettingsFrame}
 * 
 * @author theoklitos
 * 
 */
public final class DatabaseSettingsFrameControl implements IDatabaseSettingsFrameControl {

	private DatabaseSettingsFrame view;
	private final DatabaseConnection databaseConnection;
	private final Vault vault;

	@Inject
	public DatabaseSettingsFrameControl(final DatabaseConnection databaseConnection, final Vault vault) {
		this.databaseConnection = databaseConnection;
		this.vault = vault;
	}

	@Subscribe
	public void databaseUpdated(final DatabaseModificationEvent event) {
		updateDatabaseInformationInView();
	}
	
	@Override
	public String getDatabaseLocation() {
		return databaseConnection.getLocation();
	}

	@Override
	public void setView(final ControllableView view) {
		this.view = (DatabaseSettingsFrame) view;
	}

	@Override
	public void updateDatabaseInformationInView() {
		if (view != null) {
			final List<String> lines = Lists.newArrayList();
			// players
			final int pcSize = vault.getAll(IPlayerCharacter.class).size();
			final String pcMessage = pcSize != 1 ? pcSize + " player characters." : pcSize + " player character."; 
			lines.add(pcMessage);
			// types and parts			
			lines.add(vault.getAll(IType.class).size() + " types, comprised of " + vault.getAll(BodyPart.class).size()
				+ " body parts.");
			view.setDatabaseDescription(lines);
		}
	}

}
