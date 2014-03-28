package com.apprentice.rpg.gui.log;

import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.playerCharacter.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.google.common.collect.Lists;

/**
 * Control for the frame that is fed the log4j stream.
 * 
 * @author theoklitos
 * 
 */
public final class LogFrameControl extends AppenderSkeleton implements ILogFrameControl {

	public static final String LOG_TIME_PATTERN = "HH:mm:ss";

	public List<List<String>> messages;
	private ILogFrame view;

	// @Inject
	private IServiceLayer serviceLayer;

	public LogFrameControl() {
		messages = Lists.newArrayList();
	}

	@Override
	public void append(final LoggingEvent event) {
		final DateTime now = new DateTime();
		final DateTimeFormatter formatter = DateTimeFormat.forPattern(LOG_TIME_PATTERN);
		final String formattedDate = formatter.print(now);
		final List<String> message =
			Lists.newArrayList(formattedDate, event.getLevel().toString(), event.getMessage().toString());
		messages.add(message);
		checkIfViewIsActiveAndAppend(message);
	}

	private void checkIfViewIsActiveAndAppend(final List<String> message) {
		if (view != null) {
			view.appendMessage(message.get(0), message.get(1), message.get(2));
		}
	}

	@Override
	public void close() {
		// nothing
	}

	@Override
	public void createOrUpdate(final Nameable item) throws NameAlreadyExistsEx {
		serviceLayer.createOrUpdate(item);
	}

	@Override
	public void createOrUpdateUniqueName(final Nameable item) throws NameAlreadyExistsEx {
		serviceLayer.createOrUpdateUniqueName(item);
	}

	@Override
	public boolean deleteNameable(final String name, final ItemType itemType) {
		return serviceLayer.deleteNameable(name, itemType);
	}

	@Override
	public ApprenticeEventBus getEventBus() {
		return serviceLayer.getEventBus();
	}

	@Override
	public IGlobalWindowState getGlobalWindowState() {
		return serviceLayer.getGlobalWindowState();
	}

	@Override
	public String getLastUpdateTime(final String typeName, final ItemType type) {
		return serviceLayer.getLastUpdateTime(typeName, type);
	}

	@Override
	public List<List<String>> getMessages() {
		return Lists.newArrayList(messages);
	}

	@Override
	public Vault getVault() {
		return serviceLayer.getVault();
	}

	@Override
	public ILogFrame getView() {
		return view;
	}

	@Override
	public void renameNamebale(final String oldName, final String newName, final ItemType itemType)
			throws NameAlreadyExistsEx {
		serviceLayer.renameNamebale(oldName, newName, itemType);
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	public void setView(final ILogFrame view) {
		this.view = view;
	}
}
