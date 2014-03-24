package com.apprentice.rpg.gui.log;

import java.util.List;

import com.apprentice.rpg.gui.ControlForView;

/**
 * Control for the frame that is fed the log4j stream.
 * 
 * @author theoklitos
 * 
 */
public interface ILogFrameControl extends ControlForView<ILogFrame> {

	/**
	 * returns the received log messages that should be displayed in the view
	 */
	List<List<String>> getMessages();
}
