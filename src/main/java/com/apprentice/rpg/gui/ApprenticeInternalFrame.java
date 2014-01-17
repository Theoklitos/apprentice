package com.apprentice.rpg.gui;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.apache.log4j.Logger;

import com.apprentice.rpg.gui.util.WindowUtils;
import com.apprentice.rpg.util.Box;

/**
 * Common functionality for all {@link JInternalFrame}s in our program
 * 
 * @author theoklitos
 * 
 */
public class ApprenticeInternalFrame extends JInternalFrame implements ControllableView {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(ApprenticeInternalFrame.class);

	private static final long serialVersionUID = 1L;

	private final GlobalWindowState globalWindowState;

	public ApprenticeInternalFrame(final GlobalWindowState globalWindowState) {
		this.globalWindowState = globalWindowState;
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setStateListeners();
	}

	/**
	 * for closures
	 */
	private final JInternalFrame getReferenceToSelf() {
		return this;
	}

	private WindowState getWindowStateForThisFrame() {
		return new WindowState(getTitle(), getBounds());
	}

	private final void setStateListeners() {
		addInternalFrameListener(new InternalFrameListener() {

			@Override
			public void internalFrameActivated(final InternalFrameEvent arg0) {
				final Box<WindowState> oldState =
					globalWindowState.getWindowState(getClass(), getWindowStateForThisFrame());
				if (oldState.hasContent()) {
					getReferenceToSelf().setBounds(oldState.getContent().getBounds());
				} else {
					WindowUtils.centerInternalComponent(getParent(), getReferenceToSelf(), 0);
				}
			}

			@Override
			public void internalFrameClosed(final InternalFrameEvent arg0) {
				globalWindowState.updateWindow(getClass(), getWindowStateForThisFrame());
			}

			@Override
			public void internalFrameClosing(final InternalFrameEvent arg0) {
				// nothing
			}

			@Override
			public void internalFrameDeactivated(final InternalFrameEvent arg0) {
				// nothing
			}

			@Override
			public void internalFrameDeiconified(final InternalFrameEvent arg0) {
				// nothing
			}

			@Override
			public void internalFrameIconified(final InternalFrameEvent arg0) {
				// nothing
			}

			@Override
			public void internalFrameOpened(final InternalFrameEvent arg0) {
				// nothing
			}
		});
	}

}
