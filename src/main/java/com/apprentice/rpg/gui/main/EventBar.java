package com.apprentice.rpg.gui.main;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.apprentice.rpg.gui.ControllableView;

/**
 * Appears at the bottom of the window, displaying recent events/logs
 * 
 * @author theoklitos
 * 
 */
public final class EventBar extends JPanel implements ControllableView {

	private static final long serialVersionUID = 1L;

	private final JLabel text;

	public EventBar() {
		text = new JLabel(" ");
		add(text);
	}

	/**
	 * Replaces the message at the center of the bar with the given message
	 */
	public void showMessage(final String message) {
		text.setText(message);
	}
}
