package com.apprentice.rpg.gui;

import java.awt.Dimension;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.apprentice.rpg.gui.util.IWindowUtils;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.gui.windowState.WindowState;
import com.apprentice.rpg.gui.windowState.WindowStateIdentifier;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.util.Box;

/**
 * Common functionality for all {@link JInternalFrame}s in our program
 * 
 * @author theoklitos
 * 
 */
public abstract class ApprenticeInternalFrame extends JInternalFrame implements ControllableView {

	private static Logger LOG = Logger.getLogger(ApprenticeInternalFrame.class);

	private static final long serialVersionUID = 1L;

	/**
	 * determines this frame's title
	 */
	private static String getTitle(final ItemType itemType, final Box<? extends Nameable> contentBox) {
		String verb = "Create ";
		if (contentBox != null && contentBox.hasContent()) {
			verb = "Edit ";
		}
		final String noun = StringUtils.capitalize(itemType.toString());
		return verb + noun;
	}

	/**
	 * determines if this internal frame has a variable (name)
	 */
	private static String getVariable(final Box<? extends Nameable> contentBox) {
		if (contentBox == null || contentBox.isEmpty()) {
			return null;
		} else {
			return contentBox.getContent().getName();
		}
	}

	private final IGlobalWindowState globalWindowState;
	private final String variable;

	/**
	 * automatically sets the title and the variable name based on parameters passed
	 */
	public ApprenticeInternalFrame(final IGlobalWindowState globalWindowState, final ItemType itemType,
			final Box<? extends Nameable> contentBox) {
		this(globalWindowState, getTitle(itemType, contentBox), getVariable(contentBox));
	}

	public ApprenticeInternalFrame(final IGlobalWindowState globalWindowState, final String title) {
		this(globalWindowState, title, null);
	}

	public ApprenticeInternalFrame(final IGlobalWindowState globalWindowState, final String title, final String variable) {
		this.globalWindowState = globalWindowState;
		this.variable = variable;
		if (variable != null) {
			LOG.debug("Opening " + getWindowStateIdentifier().getWindowClass().getSimpleName() + " for variable: "
				+ variable);
		}

		setTitle(title);
		if (globalWindowState.getWindowState(getWindowStateIdentifier()).hasContent()) {
			if (globalWindowState.getWindowState(getWindowStateIdentifier()).getContent().isOpen()) {
				throw new FrameAlreadyOpenEx("Tried to open frame \"" + getTitle() + "\" which was already open.");
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
	 * closes this internal frame
	 */
	public final void close() {
		try {
			this.setClosed(true);
		} catch (final PropertyVetoException e) {
			this.setVisible(false);
		}
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

	/**
	 * creaates a {@link WindowStateIdentifier} for this internal frame
	 */
	private WindowStateIdentifier getWindowStateIdentifier() {
		return new WindowStateIdentifier(getClass(), variable);
	}

	/**
	 * returns a reference to the {@link IWindowUtils} that this frame has been given
	 */
	public IWindowUtils getWindowUtils() {
		return globalWindowState.getWindowUtils();
	}

	private final void setStateListeners() {
		addInternalFrameListener(new InternalFrameListener() {

			private boolean hasJustOpened;

			@Override
			public void internalFrameActivated(final InternalFrameEvent event) {
				if (hasJustOpened) {
					final Box<WindowState> preExistingState =
						globalWindowState.getWindowState(getWindowStateIdentifier());
					if (preExistingState.hasContent()) {
						getReferenceToSelf().setBounds(preExistingState.getContent().getBounds());
						globalWindowState.setWindowOpen(getWindowStateIdentifier(), true);
					} else {
						getReferenceToSelf().setSize(getInitialSize());
						globalWindowState.getWindowUtils()
								.centerInternalComponent(getParent(), getReferenceToSelf(), 0);
						globalWindowState.setWindowState(getWindowStateIdentifier(), getBounds(), true);
					}
					hasJustOpened = false;
				}
			}

			@Override
			public void internalFrameClosed(final InternalFrameEvent event) {
				globalWindowState.setWindowState(getWindowStateIdentifier(), getBounds(), false);
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
