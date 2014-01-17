package com.apprentice.rpg.gui;

import java.awt.Rectangle;

import com.google.common.base.Objects;

/**
 * Information about a window(frame)'s position and size
 * 
 * @author theoklitos
 * 
 */
public final class WindowState {

	private final String name;
	private final Rectangle bounds;

	public WindowState(final String name, final Rectangle bounds) {
		this.name = name;
		this.bounds = bounds;
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof WindowState) {
			final WindowState otherWindowState = (WindowState) other;
			return Objects.equal(getName(), otherWindowState.getName())
				&& Objects.equal(getBounds(), otherWindowState.getBounds());
		} else {
			return false;
		}
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name, getBounds());
	}

}
