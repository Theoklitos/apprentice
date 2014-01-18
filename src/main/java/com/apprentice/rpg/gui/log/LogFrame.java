package com.apprentice.rpg.gui.log;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.GlobalWindowState;
import com.apprentice.rpg.gui.util.WindowUtils;

/**
 * Frame that is fed the log4j stream.
 * 
 * @author theoklitos
 * 
 */
public final class LogFrame extends ApprenticeInternalFrame {

	private static final long serialVersionUID = -1L;

	private final JTable table;
	private final LogFrameTableModel tableModel;
	private final JScrollPane tableScrollPane;

	public LogFrame(final GlobalWindowState globalWindowState) {
		super(globalWindowState);
		setTitle("Apprentice Log");
		setSize(600, 400);

		WindowUtils.setInformationIcon(this);
		getContentPane().setLayout(new BorderLayout(0, 0));

		tableModel = new LogFrameTableModel();

		table = new JTable(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);		
		tableScrollPane = new JScrollPane(table);
		getContentPane().add(tableScrollPane, BorderLayout.CENTER);
		setVisible(true);
	}

	/**
	 * Adds the string message as a new row to the table inside this logframe
	 */
	public void appendMessage(final String date, final String type, final String message) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				tableModel.addRow(new Object[] { date, type, message });
			}
		});
	}

	/**
	 * Wipes the table
	 */
	public void clearMessages() {
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			tableModel.removeRow(i);
		}
	}
}