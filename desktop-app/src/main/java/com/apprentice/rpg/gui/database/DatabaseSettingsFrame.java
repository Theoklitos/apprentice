package com.apprentice.rpg.gui.database;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.IGlobalWindowState;
import com.google.common.base.Joiner;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class DatabaseSettingsFrame extends ApprenticeInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtfldDatabaseLocation;
	private final IDatabaseSettingsFrameControl control;
	private JLabel databaseContentsDescriptionLabel;
	private JPanel databaseContentsPanel;

	public DatabaseSettingsFrame(final IGlobalWindowState globalWindowState, final IDatabaseSettingsFrameControl control) {
		super(globalWindowState,"Database Settings");
		this.control = control;

		initComponents();
		txtfldDatabaseLocation.setText(control.getDatabaseLocation());
		control.setView(this);
		control.updateDatabaseInformationInView();
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(450, 300);
	}

	private void initComponents() {
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
					FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
					RowSpec.decode("default:grow"), }));

		final JLabel lblDatabaseLocation = new JLabel("Database Location:");
		getContentPane().add(lblDatabaseLocation, "2, 2");

		txtfldDatabaseLocation = new JTextField();
		txtfldDatabaseLocation.setEditable(false);
		getContentPane().add(txtfldDatabaseLocation, "2, 4, fill, default");
		txtfldDatabaseLocation.setColumns(10);

		final JButton btnChangeDatabase = new JButton("Change Database...");
		getContentPane().add(btnChangeDatabase, "2, 6");

		databaseContentsPanel = new JPanel();
		databaseContentsPanel.setBorder(new TitledBorder(null, "Database Contents", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		getContentPane().add(databaseContentsPanel, "2, 8, fill, fill");
		databaseContentsDescriptionLabel = new JLabel("No content");
		databaseContentsPanel.add(databaseContentsDescriptionLabel);
	}

	/**
	 * replaces the contets of the "description" panel with the given strings.Every element is one line.
	 */
	public void setDatabaseDescription(final List<String> description) {
		final StringBuffer descriptionBuffer = new StringBuffer("<html>");
		descriptionBuffer.append(Joiner.on("<br>").join(description));
		descriptionBuffer.append("</html>");
		EventQueue.invokeLater(new Runnable() {			

			@Override
			public void run() {				
				databaseContentsDescriptionLabel.setText(descriptionBuffer.toString());
			}
		});
	}
}
