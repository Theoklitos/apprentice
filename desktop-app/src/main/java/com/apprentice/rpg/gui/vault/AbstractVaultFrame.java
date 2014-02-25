package com.apprentice.rpg.gui.vault;

import java.awt.Dimension;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.ControllableView;
import com.apprentice.rpg.gui.description.DescriptionPanel;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.Nameable;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Shows a table of objects that exist in the database
 * 
 * @author theoklitos
 * 
 */
public abstract class AbstractVaultFrame extends ApprenticeInternalFrame implements ControllableView {

	private static final String DEFAULT_DESCRIPTION_PANEL_TITLE = "Description";

	private static final long serialVersionUID = 1L;

	private VaultFrameTableModel tableModel;
	private JTable table;
	private final IVaultFrameControl control;
	private final boolean shouldShowCreateButton;
	private final Class<? extends Nameable> vaultClass;

	public AbstractVaultFrame(final IGlobalWindowState globalWindowState, final String title,
			final Class<? extends Nameable> vaultClass, final IVaultFrameControl vaultFrameControl) {
		this(globalWindowState, title, vaultClass, vaultFrameControl, DEFAULT_DESCRIPTION_PANEL_TITLE, true);
	}

	public AbstractVaultFrame(final IGlobalWindowState globalWindowState, final String title,
			final Class<? extends Nameable> vaultClass, final IVaultFrameControl vaultFrameControl,
			final String descriptionPanelTitle, final boolean shouldShowCreateButton) {
		super(globalWindowState, title);
		this.vaultClass = vaultClass;
		this.control = vaultFrameControl;
		this.shouldShowCreateButton = shouldShowCreateButton;
		initComponents(descriptionPanelTitle);
		populateTable();
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(450, 300);
	}

	private void initComponents(final String descriptionPanelTitle) {
		getContentPane().setLayout(
				new FormLayout(
						new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
						new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"),
							FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(16dlu;default):grow"),
							FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), }));

		tableModel = new VaultFrameTableModel();
		table = new JTable(tableModel);
		final JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, "2, 2, fill, fill");

		final DescriptionPanel descriptionPanel =
			new DescriptionPanel(control, getWindowUtils(), descriptionPanelTitle);
		getContentPane().add(descriptionPanel, "2, 4, fill, fill");

		final JLabel descriptionLabel = new JLabel("");
		descriptionPanel.add(descriptionLabel);

		final JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, "2, 6, fill, fill");

		if (shouldShowCreateButton) {
			final JButton btnNew = new JButton("Create");
			buttonPanel.add(btnNew);
		}

		final JButton btnEdit = new JButton("Open");
		buttonPanel.add(btnEdit);
		final JButton btnExport = new JButton("Export");
		buttonPanel.add(btnExport);
		final JButton btnImport = new JButton("Import");
		buttonPanel.add(btnImport);
	}

	private void populateTable() {
		final Vault vault = control.getVault();
		final Collection<? extends Nameable> items = vault.getAllNameables(vaultClass);
		for (final Nameable item : items) {
			tableModel.addRow(new Object[] { item.getName(), vault.getPrettyUpdateTime(item) });
		}

	}

}
