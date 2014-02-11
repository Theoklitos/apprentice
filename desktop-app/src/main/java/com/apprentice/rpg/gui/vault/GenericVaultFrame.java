package com.apprentice.rpg.gui.vault;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.ControllableView;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
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
public final class GenericVaultFrame extends ApprenticeInternalFrame implements ControllableView {

	private static final long serialVersionUID = 1L;

	private VaultFrameTableModel tableModel;
	private JTable table;

	public GenericVaultFrame(final IGlobalWindowState globalWindowState) {
		super(globalWindowState,"Vault");
		getContentPane().setLayout(
				new FormLayout(
						new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
						new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"),
							FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(16dlu;default):grow"),
							FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), }));

		initComponents();
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(450, 300);
	}

	private void initComponents() {
		tableModel = new VaultFrameTableModel();
		table = new JTable(tableModel);
		final JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, "2, 2, fill, fill");

		final JPanel descriptionPanel = new JPanel();
		descriptionPanel
				.setBorder(new TitledBorder(null, "Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(descriptionPanel, "2, 4, fill, fill");

		final JLabel descriptionLabel = new JLabel("");
		descriptionPanel.add(descriptionLabel);

		final JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, "2, 6, fill, fill");

		final JButton btnNew = new JButton("Create");
		buttonPanel.add(btnNew);

		final JButton btnEdit = new JButton("Edit");
		buttonPanel.add(btnEdit);
	}

}
