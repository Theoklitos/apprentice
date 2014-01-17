package com.apprentice.rpg.gui;

import java.util.Map;
import java.util.Set;

import com.apprentice.rpg.util.Box;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Information about all the spawned windows and their positions/sizes
 * 
 * @author theoklitos
 * 
 */
public final class GlobalWindowState {

	final Map<Class<?>, Set<WindowState>> globalState;

	public GlobalWindowState() {
		globalState = Maps.newHashMap();
	}

	/**
	 * Returns box with a {@link WindowState} if such a window is registered
	 */
	public Box<WindowState> getWindowState(final Class<?> type, final WindowState windowState) {
		// check if exists
		for (final Class<?> existingType : globalState.keySet()) {
			if (existingType.equals(type)) {
				// hit! check the name
				for (final WindowState state : globalState.get(type)) {
					if (state.getName().equals(windowState.getName())) {
						return Box.with(state);
					}
				}
			}
		}
		return Box.empty();
	}

	/**
	 * updates (replaces) the {@link WindowState} information. Use the "name" property to distinguish between
	 * many different windows of the same class.
	 */
	public void updateWindow(final Class<?> type, final WindowState windowState) {
		Set<WindowState> states = globalState.get(type);
		if (states == null) {
			states = Sets.newHashSet();
		}
		for (final WindowState existingState : states) {
			if (existingState.getName().equals(windowState.getName())) {
				states.remove(existingState);
			}
		}
		states.add(windowState);
		globalState.put(type, states);
	}
}
