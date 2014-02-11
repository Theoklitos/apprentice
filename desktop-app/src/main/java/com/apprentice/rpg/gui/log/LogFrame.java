package com.apprentice.rpg.gui.log;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JScrollPane;

import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.ApprenticeTable;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;

/**
 * Frame that is fed the INFO messages from the log4j stream.
 * 
 * @author theoklitos
 * 
 */
public final class LogFrame extends ApprenticeInternalFrame implements ILogFrame {

	private static final long serialVersionUID = -1L;

	private final ApprenticeTable table;
	private final LogFrameTableModel tableModel;
	private final JScrollPane tableScrollPane;

	public LogFrame(final IGlobalWindowState globalWindowState) {
		super(globalWindowState, "Apprentice Log");

		getContentPane().setLayout(new BorderLayout(0, 0));

		tableModel = new LogFrameTableModel();
		table = new ApprenticeTable(tableModel);
		table.getColumnModel().getColumn(0).setMaxWidth(75);
		table.getColumnModel().getColumn(1).setMaxWidth(70);
		tableScrollPane = new JScrollPane(table);
		getContentPane().add(tableScrollPane, BorderLayout.CENTER);
	}
	
	@Override
	public void appendMessage(final String date, final String type, final String message) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				tableModel.addRow(new Object[] { date, type, message });
			}
		});
	}

	@Override
	public void clearMessages() {
		table.clearRows();
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(600, 400);
	}
}
