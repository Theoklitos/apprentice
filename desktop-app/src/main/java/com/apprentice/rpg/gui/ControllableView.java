package com.apprentice.rpg.gui;

/**
 * A view element that uses a {@link ControlForView} for its control
 * 
 * @author theoklitos
 * 
 */
public interface ControllableView {

	/**
	 * refreshed the displayed data from the model. This will be called when new events happen. 
	 */
	public void refreshFromModel();
}
