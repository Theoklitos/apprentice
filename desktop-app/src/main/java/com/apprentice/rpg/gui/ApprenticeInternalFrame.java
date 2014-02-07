package com.apprentice.rpg.gui;

import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.apache.log4j.Logger;

import com.apprentice.rpg.util.Box;

/**
 * Common functionality for all {@link JInternalFrame}s in our program
 * 
 * @author theoklitos
 * 
 */
public abstract class ApprenticeInternalFrame extends JInternalFrame implements ControllableView {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(ApprenticeInternalFrame.class);

	private static final long serialVersionUID = 1L;

	private final IGlobalWindowState globalWindowState;

	public ApprenticeInternalFrame(final IGlobalWindowState globalWindowState, final String title) {
		this.globalWindowState = globalWindowState;
		setTitle(title);
		if (globalWindowState.getWindowState(getTitle()).hasContent()) {
			if (globalWindowState.getWindowState(getTitle()).getContent().isOpen()) {
				//throw new FrameAlreadyOpenEx("Tried to open frame \"" + getTitle() + "\" which was already open.");
			}
		}
		globalWindowState.getWindowUtils().setInformationIcon(this);
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setStateListeners();
		setMinimumSize(getInitialSize());
		setVisible(true);
	}

	/**
	 * returns a reference to the {@link IGlobalWindowState} that this frame has been given
	 */
	public IGlobalWindowState getGlobalWindowState() {
		return globalWindowState;
	}

	/**
	 * if the user opens this window for the first time, what should its size be? Note: This will also be the
	 * minimum size.
	 */
	public abstract Dimension getInitialSize();

	/**
	 * for closures
	 */
	public final JInternalFrame getReferenceToSelf() {
		return this;
	}

	private final void setStateListeners() {
		addInternalFrameListener(new InternalFrameListener() {

			private boolean hasJustOpened;

			@Override
			public void internalFrameActivated(final InternalFrameEvent event) {
				if (hasJustOpened) {
					final Box<WindowState> oldState = globalWindowState.getWindowState(getTitle());
					if (oldState.hasContent()) {
						getReferenceToSelf().setBounds(oldState.getContent().getBounds());
						globalWindowState.setWindowOpen(getTitle());
					} else {
						getReferenceToSelf().setSize(getInitialSize());
						globalWindowState.getWindowUtils()
								.centerInternalComponent(getParent(), getReferenceToSelf(), 0);
						globalWindowState.updateWindow(getTitle(), getBounds(), true);
					}
					hasJustOpened = false;
				}
			}

			@Override
			public void internalFrameClosed(final InternalFrameEvent event) {
				globalWindowState.updateWindow(getTitle(), getBounds(), false);
			}

			@Override
			public void internalFrameClosing(final InternalFrameEvent event) {
				// nothing
			}

			@Override
			public void internalFrameDeactivated(final InternalFrameEvent event) {
				// nothing
			}

			@Override
			public void internalFrameDeiconified(final InternalFrameEvent event) {
				// nothing
			}

			@Override
			public void internalFrameIconified(final InternalFrameEvent event) {
				// nothing
			}

			@Override
			public void internalFrameOpened(final InternalFrameEvent event) {
				hasJustOpened = true;
			}
		});
	}

}
