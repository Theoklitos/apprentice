package com.apprentice.rpg.gui;


/**
 * A control element that can manage a gui {@link ControllableView} view
 * 
 * @author theoklitos
 * 
 */
public interface ControlForView {

	/**
	 * Sets the view that this control is managing
	 */
	public void setView(final ControllableView view);
}
