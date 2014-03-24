package com.apprentice.rpg.events;

/**
 * When the frames should be closed, this event is fired.
 * 
 * @author theoklitos
 */
public final class ShutdownEvent {

	private final boolean shouldReopen;

	public ShutdownEvent(final boolean shouldReopen) {
		this.shouldReopen = shouldReopen;

	}

	/**
	 * should the frame(s) reopen on startup?
	 */
	public boolean shouldReopen() {
		return shouldReopen;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ", should reopen: " + shouldReopen;
	}
}
