package com.apprentice.rpg.gui.main;

import java.util.logging.Level;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * The control for the a bar that displayes messages/notifications on the bottom of the main frame
 * 
 * @author theoklitos
 * 
 */
public class EventBarControl extends AppenderSkeleton {

	private EventBar view;
	
	@Override
	protected void append(final LoggingEvent event) {		
		if (view != null && event.getLevel().toString().equals(Level.INFO.toString())) {
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
	
	/**
	 * Sets the view i.e. the component where the messags are to be displayed
	 */
	public void setView(final EventBar view) {
		this.view = view;
		
	}
}
