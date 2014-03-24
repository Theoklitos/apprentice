package com.apprentice.rpg.gui;

import java.awt.Dimension;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.events.ApprenticeEvent;
import com.apprentice.rpg.events.ShutdownEvent;
import com.apprentice.rpg.gui.util.IWindowUtils;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.gui.windowState.WindowState;
import com.apprentice.rpg.gui.windowState.WindowStateIdentifier;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.util.Box;
import com.google.common.eventbus.Subscribe;

/**
 * Common functionality for all {@link JInternalFrame}s in our program
 * 
 * @param T
 *            The type of the control for this frame
 * 
 * @author theoklitos
 * 
 */
@SuppressWarnings("unchecked")
public abstract class ApprenticeInternalFrame<T extends IServiceLayer> extends JInternalFrame implements
		ControllableView {

	private static Logger LOG = Logger.getLogger(ApprenticeInternalFrame.class);

	private static final long serialVersionUID = 1L;

	private final IGlobalWindowState globalWindowState;
	private Nameable item;
	private final T control;

	public ApprenticeInternalFrame(final T serviceLayer, final String title) {
		this(serviceLayer, title, null, null);
	}

	public ApprenticeInternalFrame(final T control, final String title, final ItemType type, final String itemName) {
		this.control = control;
		this.globalWindowState = this.control.getGlobalWindowState();
		this.control.getEventBus().register(this);

		if (ControlForView.class.isAssignableFrom(control.getClass())) {
			((ControlForView<ApprenticeInternalFrame<T>>) control).setView(this);
		}
		if (item != null) {
			LOG.debug("Opening " + getWindowStateIdentifier().getWindowClass().getSimpleName() + " for variable: "
				+ item);
			setItem(control.getVault().getUniqueNamedResult(itemName, type.type));
		}

		if (globalWindowState.getWindowState(getWindowStateIdentifier()).hasContent()) {			
			if (globalWindowState.getWindowState(getWindowStateIdentifier()).getContent().isOpen()) {				
				throw new FrameAlreadyOpenEx("Tried to open frame \"" + getClass().getSimpleName()
					+ "\" which was already open.");
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
		setTitle(title);
	}

	/**
	 * use this to auto-determine the title
	 */
	public ApprenticeInternalFrame(final T serviceLayer, final T control, final ItemType itemType, final String itemName) {
		this(serviceLayer, "", null, null);
	}

	@Subscribe
	public void apprenticeEvent(final ApprenticeEvent event) {
		refreshFromModel();
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
	 * Returns the {@link ControlForView} that controls this frame
	 */
	public T getControl() {
		return control;
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
	 * If this apprentice frame is initialized with a {@link Nameable} variable, returns it
	 */
	public Box<Nameable> getItem() {
		if (item == null) {
			return Box.empty();
		} else {
			return Box.with(item);
		}
	}

	/**
	 * for closures
	 */
	public final JInternalFrame getReferenceToSelf() {
		return this;
	}

	/**
	 * returns null if no variable is set, otherwise returns its name
	 */
	private String getVariableName() {
		if (item == null) {
			return null;
		} else {
			return item.getName();
		}
	}

	/**
	 * creaates a {@link WindowStateIdentifier} for this internal frame
	 */
	private WindowStateIdentifier getWindowStateIdentifier() {
		return new WindowStateIdentifier(getClass(), getVariableName());
	}

	/**
	 * returns a reference to the {@link IWindowUtils} that this frame has been given
	 */
	public IWindowUtils getWindowUtils() {
		return globalWindowState.getWindowUtils();
	}

	/**
	 * sets the stored nameable variable for this frame and also sets the title
	 */
	public void setItem(final Nameable item) {
		this.item = item;
		if (StringUtils.isBlank(title)) {
			String verb = "Create ";
			if (getItem() != null && getItem().hasContent()) {
				verb = "Edit ";
			}
			final String noun = StringUtils.capitalize(ItemType.getForClass(item.getClass()).toString());
			setTitle(verb + noun);
		} else {
			setTitle(title);
		}
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

	@Subscribe
	public void shutdownEvent(final ShutdownEvent event) {
		System.out.println(getReferenceToSelf().getClass().getSimpleName() + " received event: " + event);
		if (!isClosed) {
			close();
			globalWindowState.setWindowOpen(getWindowStateIdentifier(), event.shouldReopen());
		}
	}

	/**
	 * Updates/saves the item in the backend and refreshes it in the gui
	 * 
	 * @throws NameAlreadyExistsEx
	 */
	public final void updateParameter(final Nameable item) throws NameAlreadyExistsEx {
		final boolean exists = getControl().getVault().exists(item);
		getControl().createOrUpdate(item);
		setItem(item);
		final String name = ItemType.getForClass(item.getClass()).toString();
		if (exists) {
			getWindowUtils().showInformationMessage("name " + " updated.", "Edited " + name);
		} else {
			getWindowUtils().showInformationMessage("New " + name + " named \"" + item.getName() + "\" created.",
					"New " + name);
		}
		setItem(item);
	}
}
