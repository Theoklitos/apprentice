package com.apprentice.rpg.gui.character.player.creation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.GlobalWindowState;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Window that is used to create new PCs
 * 
 * @author theoklitos
 * 
 */
public final class NewPlayerCharacterFrame extends ApprenticeInternalFrame {

	private static final long serialVersionUID = 1L;

	private final INewPlayerCharacterFrameControl control;
	private final JTextField textField;

	public NewPlayerCharacterFrame(final GlobalWindowState globalWindowState,
			final INewPlayerCharacterFrameControl control) {
		super(globalWindowState);
		setTitle("New Player Character Creation");
		this.control = control;
		getContentPane().setLayout(new BorderLayout(0, 0));

		final JPanel genericCharacterInformation = new JPanel();
		genericCharacterInformation.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)),
				"General Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		genericCharacterInformation.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
			FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		getContentPane().add(genericCharacterInformation, BorderLayout.CENTER);

		final JLabel lblNewLabel = new JLabel("New label");
		genericCharacterInformation.add(lblNewLabel, "2, 2, right, default");

		textField = new JTextField();
		genericCharacterInformation.add(textField, "4, 2, fill, default");
		textField.setColumns(10);

		final JPanel southPanel = new JPanel();
		getContentPane().add(southPanel, BorderLayout.SOUTH);

		final JButton btnCreate = new JButton("Create");
		southPanel.add(btnCreate);
		btnCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent action) {

				// control.save
			}
		});
		
		setSize(400, 400);
		setVisible(true);
	}
}
