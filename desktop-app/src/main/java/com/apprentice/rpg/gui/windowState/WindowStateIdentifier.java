package com.apprentice.rpg.gui.windowState;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.util.Box;
import com.google.common.base.Objects;

/**
 * uniquely identifies every {@link ApprenticeInternalFrame}
 * 
 * @author theoklitos
 * 
 */
public class WindowStateIdentifier {

	private final Class<?> windowClass;
	private final String parameter;

	/**
	 * contructor to be used when the frame is not to be parameterized
	 */
	public WindowStateIdentifier(final Class<?> frameClass) {
		this.windowClass = frameClass;
		this.parameter = null;
	}

	public WindowStateIdentifier(final Class<?> windowClass, final String parameter) {
		this.windowClass = windowClass;
		this.parameter = parameter;
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof WindowStateIdentifier) {
			final WindowStateIdentifier otherWindowStateIdentifier = (WindowStateIdentifier) other;
			return Objects.equal(windowClass, otherWindowStateIdentifier.windowClass)
				&& Objects.equal(parameter, otherWindowStateIdentifier.parameter);
		} else {
			return false;
		}
	}

	/**
	 * returns the parameter of the state of an internal frame in a box, if any
	 */
	public Box<String> getParameter() {
		if (StringUtils.isBlank(parameter)) {
			return Box.empty();
		} else {
			return Box.with(parameter);
		}
	}

	/**
	 * returns the {@link Class} this state refers to
	 */
	public Class<?> getWindowClass() {
		return windowClass;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(windowClass, parameter);
	}

	@Override
	public String toString() {
		if (getParameter().hasContent()) {
			return windowClass.getSimpleName() + " with parameter: " + getParameter().getContent();
		} else {
			return windowClass.getSimpleName();
		}
	}
}
