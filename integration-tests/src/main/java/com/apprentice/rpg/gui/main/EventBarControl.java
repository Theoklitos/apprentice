package com.apprentice.rpg.gui.main;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import com.apprentice.rpg.gui.ControllableView;

public class EventBarControl extends AppenderSkeleton implements IEventBarControl {

	private EventBar view;

	@Override
	protected void append(final LoggingEvent event) {
		if (view != null) {
			view.showMessage(event.getMessage().toString());
		}
	}

	@Override
	public void close() {
		// nothing
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	public void setView(final ControllableView view) {
		this.view = (EventBar) view;
	}

}
