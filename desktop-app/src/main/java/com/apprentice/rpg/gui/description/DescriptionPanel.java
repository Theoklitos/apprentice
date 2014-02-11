package com.apprentice.rpg.gui.description;

import java.awt.EventQueue;

import org.apache.log4j.Logger;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
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

	private static final String TITLE = "Description";
	private static final long serialVersionUID = 1L;
	private final IWindowUtils windowUtils;
	private Nameable object;
	private final ApprenticeEventBus eventBus;

	public DescriptionPanel(final Vault vault, final IWindowUtils windowUtils, final ApprenticeEventBus eventBus) {
		super(TITLE, TextFieldPanelType.LABEL, vault);
		this.windowUtils = windowUtils;
		this.eventBus = eventBus;
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
						LOG.info("Updated " + TITLE + " for " + object.getName());
					}
				} else {
					windowUtils
							.showErrorMessage("Either you have not selected an element or your Type is not yet consistent.");
				}
			}
		});

	}

	public void setElement(final Nameable object) {
		this.object = object;
		setText(object.getDescription());
	}

}
