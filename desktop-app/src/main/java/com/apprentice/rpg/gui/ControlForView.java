package com.apprentice.rpg.gui;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.dao.Vault;
import com.google.common.eventbus.EventBus;

/**
 * 
 * A control element that can manage a gui {@link ControllableView} view. This control has a {@link Vault} and
 * an {@link EventBus} and can perform various backend funtions
 * 
 * @author theoklitos
 * 
 */
public interface ControlForView<T extends ControllableView> extends IServiceLayer {

	/**
	 * Returns the {@link ControllableView} view this control is controlling
	 */
	T getView();

	/**
	 * Sets the view that this control is managing
	 */
	public void setView(final T view);
}
