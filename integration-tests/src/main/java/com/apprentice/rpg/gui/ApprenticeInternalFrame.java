package com.apprentice.rpg.gui;

import java.awt.Dimension;

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
public abstract class ApprenticeInternalFrame extends JInternalFrame implements ControllableView {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(ApprenticeInternalFrame.class);

	private static final long serialVersionUID = 1L;

	private final IGlobalWindowState globalWindowState;

	public ApprenticeInternalFrame(final IGlobalWindowState globalWindowState) {
		this.globalWindowState = globalWindowState;
		WindowUtils.setInformationIcon(this);
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setStateListeners();
		setMinimumSize(getInitialSize());
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

			@Override
			public void internalFrameActivated(final InternalFrameEvent event) {				
				final Box<WindowState> oldState = globalWindowState.getWindowState(getTitle());
				if (oldState.hasContent()) {
					getReferenceToSelf().setBounds(oldState.getContent().getBounds());
				} else {
					getReferenceToSelf().setSize(getInitialSize());
					WindowUtils.centerInternalComponent(getParent(), getReferenceToSelf(), 0);
					globalWindowState.updateWindow(getTitle(), getBounds(), true);
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
