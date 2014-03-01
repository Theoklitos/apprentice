package com.apprentice.rpg.gui.description;

import java.awt.EventQueue;

import org.apache.log4j.Logger;

import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.gui.ControlWithVault;
import com.apprentice.rpg.gui.util.IWindowUtils;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.util.Box;

/**
 * Used to view and set a {@link Nameable}'s description
 * 
 * @author theoklitos
 * 
 */
public class DescriptionPanel extends ModifiableTextFieldPanel {

	private static Logger LOG = Logger.getLogger(DescriptionPanel.class);

	private static final String DEFAULT_TITLE = "Description";
	private static final long serialVersionUID = 1L;
	private final IWindowUtils windowUtils;
	private Nameable object;
	private final ApprenticeEventBus eventBus;

	/**
	 * will use LABEL type and a default title
	 */
	public DescriptionPanel(final ControlWithVault control, final IWindowUtils windowUtils) {
		this(control, windowUtils, DEFAULT_TITLE, DescriptionPanelType.LABEL);
	}

	public DescriptionPanel(final ControlWithVault control, final IWindowUtils windowUtils,
			final String borderTitle, final DescriptionPanelType type) {
		super(borderTitle, type, control.getVault());
		this.windowUtils = windowUtils;
		this.eventBus = control.getEventBus();
	}

	@Override
	protected void modifyText() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (object != null) {
					final Box<String> newTextBox =
						windowUtils.showBigTextFieldDialog("Set description for \"" + object.getName() + "\"",
								object.getDescription());
					if (newTextBox.hasContent()) {
						final String description = newTextBox.getContent();
						setText(description);
						object.setDescription(description);
						getVault().update(object);
						eventBus.postUpdateEvent(object);
						LOG.info("Updated " + DEFAULT_TITLE + " for " + object.getName());
					}
				} else {
					windowUtils
							.showErrorMessage("Either you have not selected an element or your element is not yet consistent.");
				}
			}
		});

	}

	/**
	 * set the {@link Nameable} item that this panel will manage
	 */
	public void setNameable(final Nameable item) {
		if (getType().equals(DescriptionPanelType.TEXTFIELD)) {
			setType(DescriptionPanelType.LABEL);
			initComponents();
		}
		this.object = item;
		setText(item.getDescription());
	}

}
