package com.apprentice.rpg.gui.vault.armor;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.model.armor.Armor;
import com.apprentice.rpg.model.body.IType;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Used to manage {@link Armor}s
 * 
 * @author theoklitos
 * 
 */
public final class ArmorVaultFrame extends ApprenticeInternalFrame<IArmorVaultFrameControl> implements IArmorVaultFrame {

	private static final long serialVersionUID = 1L;

	public ArmorVaultFrame(final IArmorVaultFrameControl control) {
		super(control, "Armor Vault");
		getContentPane()
				.setLayout(
						new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
							ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC,
							ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
							RowSpec.decode("default:grow"), FormFactory.RELATED_GAP_ROWSPEC, }));

		final JPanel armorVaultPanel = new JPanel();
		getContentPane().add(armorVaultPanel, "2, 2, fill, fill");

		final JPanel armorConstructionPanel = new JPanel();
		armorConstructionPanel.setBorder(new TitledBorder(null, "Type Name", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		getContentPane().add(armorConstructionPanel, "4, 2, fill, fill");
		armorConstructionPanel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("20dlu"),
					FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), FormFactory.RELATED_GAP_ROWSPEC,
					RowSpec.decode("default:grow"), }));

		final JPanel typePanel = new JPanel();
		armorConstructionPanel.add(typePanel, "1, 2, fill, fill");
		typePanel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("149px"),
			FormFactory.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("pref:grow"), }, new RowSpec[] {
			FormFactory.LINE_GAP_ROWSPEC, RowSpec.decode("24px"), }));

		final JLabel lblType = new JLabel("Designated for Type:");
		typePanel.add(lblType, "1, 2, right, center");

		final JComboBox<IType> comboBox = new JComboBox<IType>();
		typePanel.add(comboBox, "3, 2, fill, default");

		final JPanel partMappingPanel = new JPanel();
		partMappingPanel.setBorder(new TitledBorder(null, "Armor Pieces", TitledBorder.LEADING, TitledBorder.TOP, null,
				null));
		armorConstructionPanel.add(partMappingPanel, "1, 4, fill, fill");
		partMappingPanel.setLayout(new BorderLayout(0, 0));

		final JPanel partMappingPanelScrollable = new JPanel();

		final JScrollPane scrollPane = new JScrollPane(partMappingPanelScrollable);
		partMappingPanel.add(scrollPane);

		final JPanel descriptionPanel = new JPanel();
		armorConstructionPanel.add(descriptionPanel, "1, 6, fill, fill");
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(400, 300);
	}

	@Override
	public void refreshFromModel() {
		// TODO !
	}
}
