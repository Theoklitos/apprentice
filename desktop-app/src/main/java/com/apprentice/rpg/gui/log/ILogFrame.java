package com.apprentice.rpg.gui.log;

import com.apprentice.rpg.gui.ControllableView;

/**
 * Frame that is fed the INFO messages from the log4j stream.
 * 
 * @author theoklitos
 * 
 */
public interface ILogFrame extends ControllableView {

	/**
	 * Adds the string message as a new row to the table inside this logframe
	 */
	void appendMessage(String date, String type, String message);

	/**
	 * Wipes the table in the view, nothing will be shown
	 */
	void clearMessages();

}
