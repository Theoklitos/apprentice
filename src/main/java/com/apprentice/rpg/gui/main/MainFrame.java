package com.apprentice.rpg.gui.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.apprentice.rpg.gui.IWindowManager;
import com.apprentice.rpg.gui.desktop.ApprenticeDesktop;
import com.apprentice.rpg.gui.util.WindowUtils;

public final class MainFrame extends JFrame {

	private static final long serialVersionUID = 1;

	private final IMainControl mainControl;
	private final ApprenticeDesktop desktop;
	private final IWindowManager windowManager;
	private final IEventBarControl eventBarControl;

	public MainFrame(final IWindowManager windowManager, final IMainControl mainControl,
			final IEventBarControl eventBarControl, final ApprenticeDesktop desktop) {
		this.windowManager = windowManager;
		this.mainControl = mainControl;
		this.eventBarControl = eventBarControl;
		this.desktop = desktop;

		setTitle("Apprentice v0.2 - Built 0 Deathuary 2666");

		setSize(1000, 600);

		setShutdownHook();

		initMenus();
		initComponents();

		WindowUtils.setDefaultIcon(this);
		WindowUtils.centerComponent(this, 100);
	}

	/**
	 * Adds the given frame to the desktop. Its preferable to have the window manager call this.
	 */
	public void addInternalFrame(final JInternalFrame internalFrame) {
		desktop.add(internalFrame);
		WindowUtils.centerInternalComponent(this, internalFrame, 50);
		desktop.validate();
	}

	private void initComponents() {
		final JPanel grandPane = new JPanel(new BorderLayout());
		grandPane.add(desktop,BorderLayout.CENTER);
		final EventBar eventBar = new EventBar();
		eventBarControl.setView(eventBar);
		grandPane.add(eventBar,BorderLayout.SOUTH);						
		add(grandPane);
	}

	private void initMenus() {
		final JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		final JMenu mnPlayerCharacters = new JMenu("Player Characters");
		menuBar.add(mnPlayerCharacters);
		final JMenuItem mntmNewPlayer = new JMenuItem("New Player");
		mntmNewPlayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				windowManager.openNewCharacterFrame();

			}
		});
		mnPlayerCharacters.add(mntmNewPlayer);
		final JMenuItem mntmSummonPlayer = new JMenuItem("Summon Player");
		mnPlayerCharacters.add(mntmSummonPlayer);
		final JMenuItem mntmExportPlayer = new JMenuItem("Export Player");
		mnPlayerCharacters.add(mntmExportPlayer);
		final JMenuItem mntmImportPlayer = new JMenuItem("Import Player");
		mnPlayerCharacters.add(mntmImportPlayer);

		final JMenu mnConfiguration = new JMenu("Configuration");
		menuBar.add(mnConfiguration);
		final JMenuItem mntmChangeBackground = new JMenuItem("Change Background");
		mnConfiguration.add(mntmChangeBackground);

		final JMenu mnLogging = new JMenu("Log");
		menuBar.add(mnLogging);
		final JMenuItem mntmOpenApprenticeLog = new JMenuItem("Open Apprentice Log");
		mntmOpenApprenticeLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				windowManager.openLogFrame();

			}
		});
		mnLogging.add(mntmOpenApprenticeLog);

		final JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		final JMenuItem mntmAbout = new JMenuItem("About Apprentice");
		mnHelp.add(mntmAbout);
		final JMenuItem mntmContact = new JMenuItem("Submit Bug Report or Feature Request...");
		mnHelp.add(mntmContact);
	}

	private void setShutdownHook() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent we) {
				mainControl.shutdownGracefully();
				System.exit(0);
			}
		});
	}

}
