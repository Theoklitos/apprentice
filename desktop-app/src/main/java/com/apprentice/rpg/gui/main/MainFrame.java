package com.apprentice.rpg.gui.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.gui.windowState.WindowState;
import com.apprentice.rpg.gui.windowState.WindowStateIdentifier;
import com.apprentice.rpg.util.Box;

public final class MainFrame extends JFrame {

	private static final long serialVersionUID = 1;

	private final IMainControl mainControl;
	private final ApprenticeDesktop desktop;
	private final IWindowManager windowManager;
	private final IEventBarControl eventBarControl;
	private final IGlobalWindowState globalState;

	public MainFrame(final IGlobalWindowState globalState, final IWindowManager windowManager,
			final IMainControl mainControl, final IEventBarControl eventBarControl, final ApprenticeDesktop desktop) {
		this.globalState = globalState;
		this.windowManager = windowManager;
		this.mainControl = mainControl;
		this.eventBarControl = eventBarControl;
		this.desktop = desktop;

		setTitle("Apprentice v0.3 - Built 9 February 2014");
		globalState.getWindowUtils().setDefaultIcon(this);
		setSizeAndPosition();
		initMenus();
		initComponents();
		setShutdownHook();
	}

	/**
	 * Adds the given frame to the desktop. Its preferable to have the window manager call this.
	 */
	public void addInternalFrame(final JInternalFrame internalFrame) {
		desktop.add(internalFrame);
		globalState.getWindowUtils().centerInternalComponent(this, internalFrame, 50);
		desktop.validate();
	}

	private void initComponents() {
		final JPanel grandPane = new JPanel(new BorderLayout());
		grandPane.add(desktop, BorderLayout.CENTER);
		final EventBar eventBar = new EventBar();
		eventBarControl.setView(eventBar);
		grandPane.add(eventBar, BorderLayout.SOUTH);
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
				windowManager.showNewCharacterFrame();

			}
		});
		mnPlayerCharacters.add(mntmNewPlayer);
		final JMenuItem mntmSummonPlayer = new JMenuItem("Summon Player");
		mnPlayerCharacters.add(mntmSummonPlayer);

		final JMenu mnDice = new JMenu("Dice Roller");
		menuBar.add(mnDice);		
		mnDice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				windowManager.showDiceRollerFrame();
			}
		});

		final JMenu mnVaults = new JMenu("Vaults");
		menuBar.add(mnVaults);
		final JMenuItem mntmPlayers = new JMenuItem("Players");
		mnVaults.add(mntmPlayers);
		final JMenu mntmItems = new JMenu("Items");
		mnVaults.add(mntmItems);
		final JMenuItem submntmWeapons = new JMenuItem("Weapons");
		mntmItems.add(submntmWeapons);
		final JMenuItem submntmArmors = new JMenuItem("Armors");
		mntmItems.add(submntmArmors);
		final JMenuItem submntmSpells = new JMenuItem("Spells");
		mntmItems.add(submntmSpells);
		final JMenuItem mntmTypes = new JMenuItem("Types & Body Parts");
		mntmTypes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				windowManager.showTypeAndBodyPartFrame();
			}
		});
		mnVaults.add(mntmTypes);

		final JMenu mnConfiguration = new JMenu("Configuration");
		menuBar.add(mnConfiguration);
		final JMenuItem mntmDatabaseSettings = new JMenuItem("Database Settings");
		mntmDatabaseSettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				windowManager.showDatabaseSettingsFrame();
			}
		});
		mnConfiguration.add(mntmDatabaseSettings);

		final JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		final JMenuItem mntmAbout = new JMenuItem("About Apprentice");
		mnHelp.add(mntmAbout);
		mnHelp.addSeparator();
		final JMenuItem mnLogging = new JMenuItem("Log");
		mnLogging.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				windowManager.showLogFrame();

			}
		});
		mnHelp.add(mnLogging);
		final JMenuItem mntmContact = new JMenuItem("Submit Bug Report or Feature Request...");
		mnHelp.add(mntmContact);
	}

	private void setShutdownHook() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent we) {
				globalState.setWindowState(new WindowStateIdentifier(getClass()), getBounds(), true);
				mainControl.shutdownGracefully();
				System.exit(0);
			}
		});
	}

	/**
	 * stores information about this frame in the {@link IGlobalWindowState}
	 */
	private void setSizeAndPosition() {
		final Box<WindowState> stateBox = globalState.getWindowState(new WindowStateIdentifier(getClass()));
		setMinimumSize(new Dimension(700, 400));
		if (stateBox.hasContent()) {
			setBounds(stateBox.getContent().getBounds());
		} else {
			setSize(1000, 600);
			globalState.getWindowUtils().centerComponent(this, 100);
		}
	}

}
