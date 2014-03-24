package com.apprentice.rpg.events;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.gui.ApprenticeInternalFrame;

/**
 * When a frame should be immediately shown
 * 
 * @author theoklitos
 */
public final class ShowFrameEvent {

	private final Class<? extends ApprenticeInternalFrame<?>> internalFrame;
	private final String parameter;

	/**
	 * when there is no parameter
	 */
	public ShowFrameEvent(final Class<? extends ApprenticeInternalFrame<?>> internalFrame) {
		this(internalFrame, null);
	}

	public ShowFrameEvent(final Class<? extends ApprenticeInternalFrame<?>> internalFrame, final String parameter) {
		this.internalFrame = internalFrame;
		this.parameter = parameter;
	}

	/**
	 * Which internal frame should be opened?
	 */
	public Class<? extends ApprenticeInternalFrame<?>> getInternalFrame() {
		return internalFrame;
	}

	/**
	 * returns the parameter. If the frame has none, will be null or empty string
	 */
	public String getParameter() {
		return parameter;
	}

	@Override
	public String toString() {
		String parameter = "";
		if (StringUtils.isNotBlank(getParameter())) {
			parameter = ", param: " + getParameter();
		}
		return "Show " + internalFrame.getSimpleName() + parameter;
	}

}
