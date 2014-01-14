package com.apprentice.rpg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.apprentice.rpg.gui.desktop.ApprenticeDesktop;
import com.apprentice.rpg.util.WindowUtils;

public final class MainFrame extends JFrame {

	private static final long serialVersionUID = -8811829817366585684L;

	private ApprenticeDesktop desktop;
	private final IMainControl mainControl;

	public MainFrame(final IMainControl mainControl) {
		this.mainControl = mainControl;
		setTitle("Apprentice");

		setSize(1000, 600);

		setShutdownHook();

		initMenus();

		WindowUtils.setDefaultIcon(this);
		WindowUtils.centerComponent(this, 100);
	}

	public void addInternalFrame(final JInternalFrame internalFrame) {
		desktop.add(internalFrame);
		WindowUtils.centerInternalComponent(this, internalFrame, 50);
		desktop.validate();
	}

	private void initMenus() {
		final JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		final JMenu mnStrikes = new JMenu("Strikes");
		menuBar.add(mnStrikes);

		final JMenu mnPlayerCharacters = new JMenu("Player Characters");
		menuBar.add(mnPlayerCharacters);

		final JMenuItem mntmNewPlayer = new JMenuItem("New Player");
		mnPlayerCharacters.add(mntmNewPlayer);

		final JMenuItem mntmSummonPlayer = new JMenuItem("Summon Player");
		mnPlayerCharacters.add(mntmSummonPlayer);

		final JMenu mnItems = new JMenu("Items");
		menuBar.add(mnItems);

		final JMenu mnManage = new JMenu("Manage");
		mnItems.add(mnManage);

		final JMenuItem mntmWeapons = new JMenuItem("Weapons");
		mnManage.add(mntmWeapons);

		final JMenuItem mntmArmors = new JMenuItem("Armors");
		mnManage.add(mntmArmors);

		final JMenuItem mntmShowFullVault = new JMenuItem("Show Full Vault");
		mnItems.add(mntmShowFullVault);

		final JMenu mnDiceRolls = new JMenu("Dice Rolls");
		menuBar.add(mnDiceRolls);

		final JMenuItem mntmOpenDiceRoller = new JMenuItem("Open Dice Roller");
		mnDiceRolls.add(mntmOpenDiceRoller);

		final JMenu mnRandomStuff = new JMenu("Random Stuff");
		menuBar.add(mnRandomStuff);

		final JMenu mnMusic = new JMenu("Music");
		menuBar.add(mnMusic);

		final JMenu mnConfiguration = new JMenu("Configuration");
		menuBar.add(mnConfiguration);

		final JMenuItem mntmSetDefaultItems = new JMenuItem("Set Default Items File");
		mnConfiguration.add(mntmSetDefaultItems);

		final JMenuItem mntmSetDefaultRules = new JMenuItem("Set Default Rules File");
		mnConfiguration.add(mntmSetDefaultRules);

		final JMenuItem mntmChangeBackground = new JMenuItem("Change Background");
		mnConfiguration.add(mntmChangeBackground);

		final JMenu mnLogging = new JMenu("Logging");
		menuBar.add(mnLogging);

		final JMenuItem mntmOpenApprenticeLog = new JMenuItem("Open Apprentice Log");
		mntmOpenApprenticeLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				mainControl.openLoggingFrame();

			}
		});
		mnLogging.add(mntmOpenApprenticeLog);

		final JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		final JMenuItem mntmAbout = new JMenuItem("About Apprentice");
		mnHelp.add(mntmAbout);
	}

	public void setDesktop(final ApprenticeDesktop desktop) {
		setContentPane(desktop);
		this.desktop = desktop;
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
