package com.apprentice.rpg.gui.windowState;

import java.awt.Rectangle;
import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.util.IWindowUtils;
import com.apprentice.rpg.util.Box;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

/**
 * Information about all the spawned windows and their positions/sizes
 * 
 * @author theoklitos
 * 
 */
public final class GlobalWindowState implements IGlobalWindowState {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(GlobalWindowState.class);

	private final Map<WindowStateIdentifier, WindowState> globalState;
	private final IWindowUtils windowUtils;

	@Inject
	public GlobalWindowState(final IWindowUtils windowUtils) {
		this.windowUtils = windowUtils;
		globalState = Maps.newHashMap();
	}

	@Override
	public Collection<WindowStateIdentifier> getAllFrameIdentifiers() {
		return globalState.keySet();
	}

	/**
	 * for debugging, prints out info about all the containd {@link WindowState}
	 */
	public String getDetailedInformation() {
		return Joiner.on("\n").withKeyValueSeparator(":").join(globalState);
	}

	@Override
	public Collection<WindowStateIdentifier> getOpenInternalFrames() {
		final Collection<WindowStateIdentifier> result = Lists.newArrayList();
		for (final WindowStateIdentifier identifier : globalState.keySet()) {
			if (ApprenticeInternalFrame.class.isAssignableFrom(identifier.getWindowClass())) {
				final WindowState state = globalState.get(identifier);
				if (state.isOpen()) {
					result.add(identifier);
				}
			}
		}
		return result;
	}

	@Override
	public Box<WindowState> getWindowState(final WindowStateIdentifier identifier) {
		// check if exists
		if (globalState.containsKey(identifier)) {
			return Box.with(globalState.get(identifier));
		} else {
			return Box.empty();
		}
	}

	@Override
	public IWindowUtils getWindowUtils() {
		return windowUtils;
	}

	@Override
	public void setWindowOpen(final WindowStateIdentifier identifier, final boolean isOpen) throws FrameNotOpenEx {
		final WindowState state = globalState.get(identifier);
		if (state == null) {
			throw new FrameNotOpenEx("Tried to change the state of frame \""
				+ identifier.getWindowClass().getSimpleName() + "\" but such a frame did not exist.");
		} else {
			globalState.put(identifier, new WindowState(state.getBounds(), isOpen));
		}
	}

	@Override
	public void setWindowState(final WindowStateIdentifier identifier, final Rectangle bounds, final boolean isOpen) {
		globalState.put(identifier, new WindowState(bounds, isOpen));
	}

	@Override
	public String toString() {
		return Joiner.on("\n").withKeyValueSeparator(":").join(globalState);
	}
}
