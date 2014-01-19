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

	private final boolean isOpen;
	private final Rectangle bounds;

	public WindowState(final Rectangle bounds, final boolean isOpen) {
		this.isOpen = isOpen;
		this.bounds = bounds;
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof WindowState) {
			final WindowState otherWindowState = (WindowState) other;
			return Objects.equal(getBounds(), otherWindowState.getBounds()) && isOpen == otherWindowState.isOpen;
		} else {
			return false;
		}
	}

	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getBounds());
	}

	public boolean isOpen() {
		return isOpen;
	}

	@Override
	public String toString() {
		String isOpenString;
		if (isOpen) {
			isOpenString = ", is open.";
		} else {
			isOpenString = ", is not open.";
		}
		return "Bounds " + bounds + isOpenString;
	}

}
