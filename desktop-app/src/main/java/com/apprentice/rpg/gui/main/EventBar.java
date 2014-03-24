package com.apprentice.rpg.gui.main;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Appears at the bottom of the window, displaying recent events/logs
 * 
 * @author theoklitos
 * 
 */
public final class EventBar extends JPanel {

	private static final long serialVersionUID = 1L;

	private final JLabel text;

	public EventBar(final EventBarControl control) {
		text = new JLabel(" ");
		add(text);
		control.setView(this);
	}

	/**
	 * Replaces the message at the center of the bar with the given message
	 */
	public void showMessage(final String message) {
		text.setText(message);
	}
}
