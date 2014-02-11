package com.apprentice.rpg.gui.log;

import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

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

	@Inject
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

	/**
	 * returns a copy of the internal message array
	 */
	protected List<List<String>> getMessages() {
		return Lists.newArrayList(messages);
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	public void setView(final ILogFrame view) {
		this.view = view;
		synchronizeMessages(this.view);
	}

	/**
	 * Sets the contents of the view to be the same with the internal message array
	 */
	private void synchronizeMessages(final ILogFrame view) {
		view.clearMessages();
		for (final List<String> message : messages) {
			view.appendMessage(message.get(0), message.get(1), message.get(2));
		}
	}
}
