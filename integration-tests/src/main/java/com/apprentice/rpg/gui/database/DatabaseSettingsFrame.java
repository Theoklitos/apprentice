package com.apprentice.rpg.gui.database;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.IGlobalWindowState;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class DatabaseSettingsFrame extends ApprenticeInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtfldDatabaseLocation;
	private final IDatabaseSettingsFrameControl control;

	public DatabaseSettingsFrame(final IGlobalWindowState globalWindowState, final IDatabaseSettingsFrameControl control) {
		super(globalWindowState);
		this.control = control;
		setTitle("Database Settings");

		initComponents();
		txtfldDatabaseLocation.setText(control.getDatabaseLocation());
				
		setVisible(true);
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(450, 300);
	}

	private void initComponents() {
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));

		final JLabel lblDatabaseLocation = new JLabel("Database Location:");
		getContentPane().add(lblDatabaseLocation, "2, 2");

		txtfldDatabaseLocation = new JTextField();
		txtfldDatabaseLocation.setEditable(false);
		getContentPane().add(txtfldDatabaseLocation, "2, 4, fill, default");
		txtfldDatabaseLocation.setColumns(10);

		final JButton btnChangeDatabase = new JButton("Change Database...");
		getContentPane().add(btnChangeDatabase, "2, 6");

		final JPanel databaseContentsPanel = new JPanel();
		databaseContentsPanel.setBorder(new TitledBorder(null, "Database Contents", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		getContentPane().add(databaseContentsPanel, "2, 8, fill, fill");
	}
}
