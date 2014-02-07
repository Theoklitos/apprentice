package com.apprentice.rpg.gui;

import java.awt.Rectangle;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apprentice.rpg.gui.util.IWindowUtils;
import com.apprentice.rpg.util.Box;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

/**
 * Information about all the spawned windows and their positions/sizes
 * 
 * @author theoklitos
 * 
 */
public final class GlobalWindowState implements IGlobalWindowState {

	private static Logger LOG = Logger.getLogger(GlobalWindowState.class);

	private final Map<String, WindowState> globalState;
	private final IWindowUtils windowUtils;
	
	@Inject
	public GlobalWindowState(final IWindowUtils windowUtils) {
		this.windowUtils = windowUtils;
		globalState = Maps.newHashMap();
	}

	/**
	 * for debugging, prints out info about all the containd {@link WindowState}
	 * 
	 * @return
	 */
	public String getDetailedInformation() {
		return Joiner.on("\n").withKeyValueSeparator(":").join(globalState);
	}

	@Override
	public Box<WindowState> getWindowState(final String name) {
		// check if exists
		for (final String existingType : globalState.keySet()) {
			if (existingType.equals(name)) {
				return Box.with(globalState.get(name));
			}
		}
		return Box.empty();
	}

	@Override
	public IWindowUtils getWindowUtils() {
		return windowUtils;
	}

	@Override
	public void setWindowOpen(final String name) {
		final WindowState state = globalState.get(name);
		if (name == null) {
			LOG.error("Tried to change the state of frame \"" + name + "\" but such a frame did not exist.");
		} else {
			globalState.put(name, new WindowState(state.getBounds(), true));
		}
	}

	@Override
	public String toString() {
		return "Holding states for " + globalState.keySet().size() + " frame classes (" + globalState.size()
			+ " instances)";
	}

	@Override
	public void updateWindow(final String name, final Rectangle bounds, final boolean isOpen) {
		globalState.put(name, new WindowState(bounds, isOpen));
	}
}
